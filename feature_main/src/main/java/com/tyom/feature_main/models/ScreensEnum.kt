package com.tyom.feature_main.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tyom.core_ui.R
import com.tyom.feature_main.models.Routes.HOME_ROUTE
import com.tyom.feature_main.models.Routes.LIBRARY_ROUTE


enum class ScreensEnum(
    val route: String,
    @StringRes val textId: Int,
    @DrawableRes val iconId: Int,
    @DrawableRes val iconFilledId: Int,
) {
    HOME(
        route = HOME_ROUTE,
        textId = R.string.home_screen,
        iconId = R.drawable.ic_home,
        iconFilledId = R.drawable.ic_home_filled
    ),

    SETTINGS(
        route = LIBRARY_ROUTE,
        textId = R.string.settings_screen,
        iconId = R.drawable.ic_settings,
        iconFilledId = R.drawable.ic_settings_filled
    )
}
