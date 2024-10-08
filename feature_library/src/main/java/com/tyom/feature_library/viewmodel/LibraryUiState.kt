package com.tyom.feature_library.viewmodel

import com.tyom.core_ui.enums.FontEnum
import com.tyom.core_ui.models.PianoConfiguration
import com.tyom.core_utils.extensions.empty
import com.tyom.domain.models.MusicalComposition

data class LibraryUiState(
    val titleForSave: String = String.empty(),
    val fontStyle: FontEnum = FontEnum.PLAYFAIR_DISPLAY_ITALIC,
    val isNeedToReturnElementHeight: Boolean = false,

    val pianoConfiguration: PianoConfiguration = PianoConfiguration(),
    val compositions: List<MusicalComposition> = emptyList(),
    val selectedComposition: MusicalComposition = MusicalComposition(),

    val isModalBottomSheetIsOpened: Boolean = false
)