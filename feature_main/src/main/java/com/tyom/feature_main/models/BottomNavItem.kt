package com.tyom.feature_main.models

import androidx.annotation.DrawableRes

data class BottomNavigationItem(
    val screen: ScreensEnum = ScreensEnum.HOME,
    val isSelected: Boolean = false
) {
    fun getIcon(isSelected: Boolean): Int = if (isSelected) screen.iconFilledId else screen.iconId
}
