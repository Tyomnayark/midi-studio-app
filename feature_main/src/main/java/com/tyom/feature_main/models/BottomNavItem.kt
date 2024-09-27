package com.tyom.feature_main.models

import androidx.annotation.StringRes
import com.tyom.core_ui.R

data class BottomNavigationItem(
    val screen: ScreensEnum = ScreensEnum.RECORD,
    @StringRes val resIdString: Int = R.string.common_empty,
    val isSelected: Boolean = false
)