package com.tyom.feature_main.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.media.midi.MidiReceiver
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.tyom.domain.models.Instrument
import com.tyom.domain.models.toInstrument
import com.tyom.domain.usecases.CheckHaveConnectedInstrumentUseCase
import com.tyom.domain.usecases.ConnectBluetoothDeviceUseCase
import com.tyom.domain.usecases.GetMIDIInstrumentsUseCase
import com.tyom.feature_main.models.BottomNavigationItem
import com.tyom.feature_main.models.ScreensEnum
import com.tyom.feature_main.utils.hasBluetoothPermissions
import com.tyom.feature_main.utils.hasLocationPermissions
import com.tyom.feature_main.utils.toPianoPair
import com.tyom.utils.BuildConfig
import com.tyom.utils.constants.BuildTypeConstants.DEBUG_TYPE
import com.tyom.utils.extensions.launchOnDefault
import com.tyom.utils.extensions.launchOnIO
import com.tyom.utils.extensions.launchOnMain
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

            _uiState.update { state ->
                state.copy(
                    selectedInstrument = instrument,
                    bottomItems = bottomItems
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
        launchOnMain { // TODO:  @Tyom [9/10/24] { поменять на дефолт, мешают подсказки }
            if (hasLocationPermissions(context) && hasBluetoothPermissions(context)) {
                val bluetoothDevices = getMIDIInstrumentsUseCase.execute()
                val instruments = bluetoothDevices.map { bluetoothDevice ->
                    bluetoothDevice.toInstrument()
                }
                _uiState.update { state ->
                    state.copy(
                        bluetoothDevices = bluetoothDevices,
                        instruments = instruments
                    )
                }
            } else {
                Toast.makeText(context, "Не все разрешения предоставлены", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun onClickSelectInstrument(instrument: Instrument) {
        launchOnIO {
            _uiState.value.bluetoothDevices.find { device ->
                device?.address == instrument.address
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
                            val pianoPair = note.toPianoPair()

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
                connectBluetoothDeviceUseCase.execute(device, receiverImpl)
            }
        }
    }
}
