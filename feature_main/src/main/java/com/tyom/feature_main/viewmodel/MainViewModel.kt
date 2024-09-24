package com.tyom.feature_main.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.media.midi.MidiReceiver
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.tyom.domain.models.Instrument
import com.tyom.domain.models.toInstrument
import com.tyom.domain.usecases.AddInstrumentToPreferencesUseCase
import com.tyom.domain.usecases.CheckHaveConnectedInstrumentUseCase
import com.tyom.domain.usecases.ConnectBluetoothDeviceUseCase
import com.tyom.domain.usecases.GetMIDIInstrumentsUseCase
import com.tyom.feature_main.models.BottomNavigationItem
import com.example.feature_home.models.Note
import com.tyom.feature_main.models.ScreensEnum
import com.example.feature_home.models.SettingsState
import com.example.feature_home.models.toNote
import com.tyom.feature_main.utils.hasBluetoothPermissions
import com.tyom.feature_main.utils.hasLocationPermissions
import com.tyom.core_utils.BuildConfig
import com.tyom.core_utils.constants.BuildTypeConstants.DEBUG_TYPE
import com.tyom.core_utils.extensions.launchOnDefault
import com.tyom.core_utils.extensions.launchOnIO
import com.tyom.core_utils.extensions.launchOnMain
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val checkHaveConnectedInstrumentUseCase: CheckHaveConnectedInstrumentUseCase,
    private val addInstrumentToPreferencesUseCase: AddInstrumentToPreferencesUseCase,
    private val getMIDIInstrumentsUseCase: GetMIDIInstrumentsUseCase,
    private val connectBluetoothDeviceUseCase: ConnectBluetoothDeviceUseCase
) : ViewModel() {

    val _uiState = MutableStateFlow(MainUIState())
    val uiState: StateFlow<MainUIState> = _uiState.asStateFlow()

    init {
        launchOnDefault {
            val instrument = checkHaveConnectedInstrumentUseCase.execute()

            val bottomItems = listOf(
                BottomNavigationItem(
                    screen = ScreensEnum.HOME,
                    isSelected = true
                ),
                BottomNavigationItem(
                    screen = ScreensEnum.SETTINGS,
                    isSelected = false
                )
            )

            val liveNotes = listOf(
                listOf(com.example.feature_home.models.Note(value = 9, isWhiteKey = true)) to 1,
                listOf(
                    com.example.feature_home.models.Note(value = 20, isWhiteKey = true),
                    com.example.feature_home.models.Note(value = 10, isWhiteKey = true)
                ) to 2,
                listOf(
                    com.example.feature_home.models.Note(value = 66, isWhiteKey = true),
                    com.example.feature_home.models.Note(value = 1, isWhiteKey = true)
                ) to 3,
                listOf(
                    com.example.feature_home.models.Note(value = 2, isWhiteKey = true),
                    com.example.feature_home.models.Note(value = 0, isWhiteKey = true)
                ) to 4,
                listOf(
                    com.example.feature_home.models.Note(value = 9, isWhiteKey = true),
                    com.example.feature_home.models.Note(value = 4, isWhiteKey = true)
                ) to 5,
                listOf(
                    com.example.feature_home.models.Note(value = 9, isWhiteKey = true),
                    com.example.feature_home.models.Note(value = 4, isWhiteKey = true)
                ) to 6
            )

            val settingsState = com.example.feature_home.models.SettingsState(
                selectedInstrument = instrument
            )
            _uiState.update { state ->
                state.copy(
                    settingsState = settingsState,
                    bottomItems = bottomItems,
                    liveNotes = liveNotes
                )
            }
        }
    }

    fun onClickBottomItem(bottomItem: BottomNavigationItem) {
        launchOnDefault {
            val updatedBottomNavItemsList = _uiState.value.bottomItems.map { item ->
                if (item.screen == bottomItem.screen) {
                    item.copy(
                        isSelected = true
                    )
                } else {
                    item.copy(
                        isSelected = false
                    )
                }
            }

            _uiState.update { state ->
                state.copy(
                    bottomItems = updatedBottomNavItemsList
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun onClickRefreshInstruments() {
        if (!_uiState.value.settingsState.isLoading) {
            launchOnMain { // TODO:  @Tyom [9/10/24] { поменять на дефолт, мешают подсказки }
                if (hasLocationPermissions(context) && hasBluetoothPermissions(context)) {
                    val settingsState = _uiState.value.settingsState.copy(
                        isLoading = true
                    )
                    _uiState.update { state ->
                        state.copy(
                            settingsState = settingsState
                        )
                    }

                    val bluetoothDevices = getMIDIInstrumentsUseCase.execute()
                    val instruments = bluetoothDevices.map { bluetoothDevice ->
                        bluetoothDevice.toInstrument()
                    }.toMutableList()
                    instruments.remove(_uiState.value.settingsState.selectedInstrument)

                    val finalSettingsState = _uiState.value.settingsState.copy(
                        instruments = instruments,
                        isInstrumentsListOpened = true,
                        isLoading = false
                    )
                    _uiState.update { state ->
                        state.copy(
                            bluetoothDevices = bluetoothDevices,
                            settingsState = finalSettingsState
                        )
                    }
                } else {
                    Toast.makeText(context, "Не все разрешения предоставлены", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    fun onClickSelectInstrument(instrument: Instrument) {
        launchOnIO {
            val newInstrumentsList = _uiState.value.settingsState.instruments.toMutableList()
            newInstrumentsList.remove(instrument)
            val settingsState = _uiState.value.settingsState.copy(
                instruments = newInstrumentsList,
                selectedInstrument = instrument,
                isTryingToConnect = true,
                isInstrumentsListOpened = true
            )
            _uiState.update { state ->
                state.copy(
                    settingsState = settingsState
                )
            }
            addInstrumentToPreferencesUseCase.execute(instrument)

            _uiState.value.bluetoothDevices.find { device ->
                (device?.name == instrument.name) || (device?.address == instrument.address)
            }?.let { device ->
                val receiverImpl = object : MidiReceiver() {
                    override fun onSend(msg: ByteArray, offset: Int, count: Int, timestamp: Long) {

                        synchronized(_uiState) {
                            val status = msg[0].toInt() and 0xFF
                            val velocity = msg[1].toInt() and 0xFF
                            val note = msg[2].toInt() and 0xFF

                            if (BuildConfig.BUILD_TYPE == DEBUG_TYPE) {
                                Log.d(
                                    "MidiProvider",
                                    "MIDI message: Status=$status, Note=$note, Velocity=$velocity"
                                )
                                Log.d(
                                    "MidiProvider",
                                    "MIDI message: msg=$msg, offset=$offset, count=$count, timestamp=$timestamp"
                                )
                            }
                            val pianoPair = note.toNote()

                            val updatedList = _uiState.value.currentNotes.toMutableList()
                            if (updatedList.contains(pianoPair)) {
                                updatedList.remove(pianoPair)
                            } else {
                                updatedList.add(pianoPair)
                            }
                            _uiState.update { state ->
                                state.copy(
                                    currentNotes = updatedList
                                )
                            }
                        }
                    }
                }
                val result = connectBluetoothDeviceUseCase.execute(device, receiverImpl)
                val finalSettingsState = _uiState.value.settingsState.copy(
                    isTryingToConnect = false,
                    isConnected = result
                )
                _uiState.update { state ->
                    state.copy(
                        settingsState = finalSettingsState
                    )
                }
            }
        }
    }

    fun changeKeyboardVisibility() {
        val isKeyboardVisible = _uiState.value.settingsState.isKeyboardVisible
        val settingsState = _uiState.value.settingsState.copy(
            isKeyboardVisible = !isKeyboardVisible
        )
        _uiState.update { state ->
            state.copy(
                settingsState = settingsState
            )
        }
    }

    fun changeAutoConnect() {
        val isAutoConnect = _uiState.value.settingsState.isAutoConnect
        val settingsState = _uiState.value.settingsState.copy(
            isAutoConnect = !isAutoConnect
        )
        _uiState.update { state ->
            state.copy(
                settingsState = settingsState
            )
        }
    }
}
