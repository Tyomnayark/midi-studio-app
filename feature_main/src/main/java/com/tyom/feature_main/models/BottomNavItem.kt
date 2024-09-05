package com.tyom.feature_main.models

data class BottomNavigationItem(
    val screen: ScreensEnum = ScreensEnum.HOME,
    val isSelected: Boolean = false
)