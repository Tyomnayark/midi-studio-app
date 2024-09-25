package com.tyom.feature_library.viewmodel

import com.tyom.core_ui.enums.FontEnum

data class LibraryUiState(
    val titleForSave: String = "",
    val fontStyle: FontEnum = FontEnum.PLAYFAIR_DISPLAY_ITALIC
)