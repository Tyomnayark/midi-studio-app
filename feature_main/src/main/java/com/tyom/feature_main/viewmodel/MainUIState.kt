package com.tyom.feature_main.viewmodel

import com.tyom.domain.models.Instrument
import com.tyom.ui_tools.interfaces.UIState


data class MainUIState(
    val selectedInstrument: Instrument? = null
): UIState