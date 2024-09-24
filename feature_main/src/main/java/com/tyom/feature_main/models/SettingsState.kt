package com.tyom.feature_main.models

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.tyom.domain.models.Instrument
import com.tyom.feature_main.R
import com.tyom.feature_main.ui.theme.GrayLight
import com.tyom.feature_main.ui.theme.Red
import com.tyom.utils.extensions.isNotNull

data class SettingsState(
    val isLoading: Boolean = false,
    val isKeyboardVisible: Boolean = true,
    val isConnected: Boolean = false,
    val isInstrumentsListOpened: Boolean = false,
    val isTryingToConnect: Boolean = false,
    val selectedInstrument: Instrument? = null,
    val instruments: List<Instrument> = emptyList()
) {
    val isConnectBtnEnabled: Boolean = selectedInstrument.isNotNull()

    @get: StringRes
    val connectBtnText: Int
        get() = if (isConnected) R.string.connected else R.string.not_connected

    val connectBtnColor: Color
        get() = if (isConnected || isTryingToConnect) Red else GrayLight
}