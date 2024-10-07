package com.example.feature_record.models

import androidx.annotation.StringRes
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.tyom.core_ui.R
import com.tyom.core_ui.theme.GrayLight
import com.tyom.core_ui.theme.Red
import com.tyom.core_utils.extensions.isNotNull
import com.tyom.domain.models.Instrument

data class SettingsState(
    val isVertical: Boolean = false,
    val isBluetoothLoading: Boolean = false,
    val isMidiLoading: Boolean = false,
    val isAutoConnect: Boolean = true,
    val isKeyboardVisible: Boolean = true,
    val isBluetoothConnected: Boolean = false,
    val isMidiConnected: Boolean = false,
    val isBluetoothInstrumentsListOpened: Boolean = false,
    val isMidiInstrumentsListOpened: Boolean = false,
    val isTryingToConnectBluetooth: Boolean = false,
    val isTryingToConnectMidi: Boolean = false,

    val selectedBluetoothInstrument: Instrument? = null,
    val bluetoothInstruments: List<Instrument> = emptyList(),

    val selectedMidiInstrument: Instrument? = null,
    val midiInstruments: List<Instrument> = emptyList()
) {
    val keyboardScale: Float = if (isVertical) 0.5f else 0.95f

    val keyboardAngle: Float = if (isVertical) 90f else 180f

    val keyboardAlign: Alignment = if (isVertical) Alignment.BottomCenter else Alignment.CenterStart

    val isConnectBluetoothBtnEnabled: Boolean = selectedBluetoothInstrument.isNotNull()

    val isConnectMidiBtnEnabled: Boolean = selectedMidiInstrument.isNotNull()

    @get: StringRes
    val connectBluetoothBtnText: Int
        get() = if (isBluetoothConnected) R.string.connected else R.string.not_connected

    @get: StringRes
    val connectMidiBtnText: Int
        get() = if (isMidiConnected) R.string.connected else R.string.not_connected

    val connectBluetoothBtnColor: Color
        get() = if (isBluetoothConnected || isTryingToConnectBluetooth) Red else GrayLight

    val connectMidiBtnColor: Color
        get() = if (isMidiConnected || isTryingToConnectMidi) Red else GrayLight
}