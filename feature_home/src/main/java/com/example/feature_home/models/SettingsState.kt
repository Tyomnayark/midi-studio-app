package com.example.feature_home.models

import androidx.annotation.StringRes
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.tyom.domain.models.Instrument
import com.tyom.core_ui.R
import com.tyom.core_ui.theme.GrayLight
import com.tyom.core_ui.theme.Red
import com.tyom.core_utils.extensions.isNotNull

data class SettingsState(
    val isVertical: Boolean = false,
    val isLoading: Boolean = false,
    val isAutoConnect: Boolean = true,
    val isKeyboardVisible: Boolean = true,
    val isConnected: Boolean = false,
    val isInstrumentsListOpened: Boolean = false,
    val isTryingToConnect: Boolean = false,
    val selectedInstrument: Instrument? = null,
    val instruments: List<Instrument> = emptyList()
) {
    val keyboardScale: Float = if (isVertical) 0.5f else 0.95f

    val keyboardAngle: Float = if (isVertical) 90f else 180f

    val keyboardAlign: Alignment = if (isVertical) Alignment.BottomCenter else Alignment.CenterStart

    val isConnectBtnEnabled: Boolean = selectedInstrument.isNotNull()

    @get: StringRes
    val connectBtnText: Int
        get() = if (isConnected) R.string.connected else R.string.not_connected

    val connectBtnColor: Color
        get() = if (isConnected || isTryingToConnect) Red else GrayLight
}