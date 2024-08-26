package com.tyom.notestudio.viewmodel

import com.tyom.domain.models.Instrument
import com.tyom.notestudio.interfaces.UIState

data class MainUIState(
    val selectedInstrument: Instrument? = null
): UIState