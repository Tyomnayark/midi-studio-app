package com.tyom.feature_main.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.media.midi.MidiDeviceInfo
import android.media.midi.MidiReceiver
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.feature_record.models.SettingsState
import com.tyom.core_utils.BuildConfig
import com.tyom.core_utils.constants.BuildTypeConstants.DEBUG_TYPE
import com.tyom.core_utils.extensions.launchOnDefault
import com.tyom.core_utils.extensions.launchOnIO
import com.tyom.core_utils.utils.hasBluetoothPermissions
import com.tyom.core_utils.utils.hasLocationPermissions
import com.tyom.domain.models.Instrument
import com.tyom.domain.models.toInstrument
import com.tyom.domain.models.toNote
import com.tyom.domain.usecases.AddInstrumentBluetoothToPreferencesUseCase
import com.tyom.domain.usecases.AddInstrumentMidiToPreferencesUseCase
import com.tyom.domain.usecases.CheckHaveConnectedInstrumentBluetoothUseCase
import com.tyom.domain.usecases.CheckHaveConnectedInstrumentMidiUseCase
import com.tyom.domain.usecases.CheckIsAutoConnectUseCase
import com.tyom.domain.usecases.ConnectBluetoothDeviceUseCase
import com.tyom.domain.usecases.ConnectMidiDeviceUseCase
import com.tyom.domain.usecases.GetBluetoothInstrumentsUseCase
import com.tyom.domain.usecases.GetMidiInstrumentsUseCase
import com.tyom.domain.usecases.SetAutoConnectUseCase
import com.tyom.feature_main.models.BottomNavigationItem
import com.tyom.feature_main.models.ScreensEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.math.absoluteValue

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val checkHaveConnectedInstrumentBluetoothUseCase: CheckHaveConnectedInstrumentBluetoothUseCase,
    private val checkHaveConnectedInstrumentMidiUseCase: CheckHaveConnectedInstrumentMidiUseCase,
    private val checkIsAutoConnectUseCase: CheckIsAutoConnectUseCase,
    private val setAutoConnectUseCase: SetAutoConnectUseCase,
    private val addInstrumentBluetoothToPreferencesUseCase: AddInstrumentBluetoothToPreferencesUseCase,
    private val addInstrumentMidiToPreferencesUseCase: AddInstrumentMidiToPreferencesUseCase,
    private val getBluetoothInstrumentsUseCase: GetBluetoothInstrumentsUseCase,
    private val getMidiInstrumentsUseCase: GetMidiInstrumentsUseCase,
    private val connectBluetoothDeviceUseCase: ConnectBluetoothDeviceUseCase,
    private val connectMidiDeviceUseCase: ConnectMidiDeviceUseCase
) : ViewModel() {

    val _uiState = MutableStateFlow(MainUIState())
    val uiState: StateFlow<MainUIState> = _uiState.asStateFlow()

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

                val updatedCurrentNotes = _uiState.value.currentNotes.toMutableList()

                if (updatedCurrentNotes.contains(pianoPair)) {
                    updatedCurrentNotes.remove(pianoPair)
                    pianoPair.isRemoveCommand = true
                } else {
                    updatedCurrentNotes.add(pianoPair)
                }

                pianoPair.time = timestamp.toInt()
                val mapSize = _uiState.value.mapSize
                val orderNumber =
                    if ((timestamp - _uiState.value.lastTimeStamp).absoluteValue < _uiState.value.pianoConfiguration.speed) mapSize else mapSize + 1
                val updatedMap = _uiState.value.liveNotes.toMutableMap()
                val listNotes = updatedMap[orderNumber]?.toMutableList() ?: mutableListOf()
                listNotes.add(pianoPair)
                updatedMap[orderNumber] = listNotes

                _uiState.update { state ->
                    state.copy(
                        currentNotes = updatedCurrentNotes,
                        liveNotes = updatedMap,
                        lastTimeStamp = timestamp,
                        mapSize = orderNumber
                    )
                }
            }
        }
    }

    init {
        launchOnDefault {
            val instrumentBluetooth = checkHaveConnectedInstrumentBluetoothUseCase.execute()
            val instrumentMidi = checkHaveConnectedInstrumentMidiUseCase.execute()

            val isAutoConnect = checkIsAutoConnectUseCase.execute()

            val bottomItems = listOf(
                BottomNavigationItem(
                    screen = ScreensEnum.RECORD,
                    resIdString = ScreensEnum.RECORD.textId,
                    isSelected = true
                ),
                BottomNavigationItem(
                    screen = ScreensEnum.LIBRARY,
                    resIdString = ScreensEnum.LIBRARY.textId,
                    isSelected = false
                )
            )
            if (isAutoConnect) {
                val midiDevices = getMidiInstrumentsUseCase.execute()

                midiDevices.find { instrument -> instrument?.properties?.getString(MidiDeviceInfo.PROPERTY_NAME) == instrumentMidi?.name }
                    ?.let { instrument ->
                        connectMidiDeviceUseCase.execute(instrument, receiverImpl)
                    }
            }

            val settingsState = SettingsState(
                selectedBluetoothInstrument = instrumentBluetooth,
                selectedMidiInstrument = instrumentMidi,
                isAutoConnect = isAutoConnect
            )

            _uiState.update { state ->
                state.copy(
                    settingsState = settingsState,
                    bottomItems = bottomItems,
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
    fun onClickRefreshBluetoothInstruments() {
        if (!_uiState.value.settingsState.isBluetoothLoading) {
            launchOnIO {
                if (hasLocationPermissions(context) && hasBluetoothPermissions(context)) {
                    val settingsState = _uiState.value.settingsState.copy(
                        isBluetoothLoading = true
                    )
                    _uiState.update { state ->
                        state.copy(
                            settingsState = settingsState
                        )
                    }

                    val bluetoothDevices = getBluetoothInstrumentsUseCase.execute()
                    val instruments = bluetoothDevices.map { bluetoothDevice ->
                        bluetoothDevice.toInstrument()
                    }.toMutableList()
                    instruments.remove(_uiState.value.settingsState.selectedBluetoothInstrument)

                    val finalSettingsState = _uiState.value.settingsState.copy(
                        bluetoothInstruments = instruments,
                        isBluetoothInstrumentsListOpened = true,
                        isBluetoothLoading = false
                    )
                    _uiState.update { state ->
                        state.copy(
                            bluetoothDevices = bluetoothDevices,
                            settingsState = finalSettingsState
                        )
                    }
                } else {
                    // TODO:  @Tyom [10/5/24] { add permission request or hint }
                }
            }
        }
    }

    fun onClickRefreshMidiInstruments() {
        if (!_uiState.value.settingsState.isMidiLoading) {
            launchOnIO {
                val settingsState = _uiState.value.settingsState.copy(
                    isMidiLoading = true
                )
                _uiState.update { state ->
                    state.copy(
                        settingsState = settingsState
                    )
                }

                val midiDevices = getMidiInstrumentsUseCase.execute()
                val instruments = midiDevices.map { bluetoothDevice ->
                    bluetoothDevice.toInstrument()
                }.toMutableList()
                instruments.remove(_uiState.value.settingsState.selectedMidiInstrument)

                val finalSettingsState = _uiState.value.settingsState.copy(
                    midiInstruments = instruments,
                    isMidiInstrumentsListOpened = true,
                    isMidiLoading = false
                )
                _uiState.update { state ->
                    state.copy(
                        midiDevices = midiDevices,
                        settingsState = finalSettingsState
                    )
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun onClickSelectBluetoothInstrument(instrument: Instrument) {
        launchOnIO {
            val newInstrumentsList =
                _uiState.value.settingsState.bluetoothInstruments.toMutableList()
            newInstrumentsList.remove(instrument)
            val settingsState = _uiState.value.settingsState.copy(
                bluetoothInstruments = newInstrumentsList,
                selectedBluetoothInstrument = instrument,
                isTryingToConnectBluetooth = true,
                isBluetoothInstrumentsListOpened = true
            )
            _uiState.update { state ->
                state.copy(
                    settingsState = settingsState
                )
            }
            addInstrumentBluetoothToPreferencesUseCase.execute(instrument)

            _uiState.value.bluetoothDevices.find { device ->
                (device?.name == instrument.name) || (device?.address == instrument.address)
            }?.let { device ->

                val result = connectBluetoothDeviceUseCase.execute(device, receiverImpl)
                val finalSettingsState = _uiState.value.settingsState.copy(
                    isTryingToConnectBluetooth = false,
                    isBluetoothConnected = result
                )
                _uiState.update { state ->
                    state.copy(
                        settingsState = finalSettingsState
                    )
                }
            }
        }
    }

    fun onClickSelectMidiInstrument(instrument: Instrument) {
        launchOnIO {
            val newInstrumentsList = _uiState.value.settingsState.midiInstruments.toMutableList()
            newInstrumentsList.remove(instrument)
            val settingsState = _uiState.value.settingsState.copy(
                midiInstruments = newInstrumentsList,
                selectedMidiInstrument = instrument,
                isTryingToConnectMidi = true,
                isMidiInstrumentsListOpened = true
            )
            _uiState.update { state ->
                state.copy(
                    settingsState = settingsState
                )
            }
            addInstrumentMidiToPreferencesUseCase.execute(instrument)

            _uiState.value.midiDevices.find { device ->
                (device?.id == instrument.id)
            }?.let { device ->

                val result = connectMidiDeviceUseCase.execute(device, receiverImpl)
                val finalSettingsState = _uiState.value.settingsState.copy(
                    isTryingToConnectMidi = false,
                    isMidiConnected = result
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
        launchOnDefault {
            val isAutoConnect = _uiState.value.settingsState.isAutoConnect
            val settingsState = _uiState.value.settingsState.copy(
                isAutoConnect = !isAutoConnect
            )

            setAutoConnectUseCase.execute(!isAutoConnect)

            _uiState.update { state ->
                state.copy(
                    settingsState = settingsState
                )
            }
        }
    }
}
