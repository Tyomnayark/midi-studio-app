package com.tyom.feature_main.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tyom.core_ui.R
import com.tyom.feature_main.models.Routes.RECORD_ROUTE
import com.tyom.feature_main.models.Routes.LIBRARY_ROUTE


enum class ScreensEnum(
    val route: String,
    @StringRes val textId: Int,
    @DrawableRes val iconId: Int,
    @DrawableRes val iconFilledId: Int,
) {
    RECORD(
        route = RECORD_ROUTE,
        textId = R.string.record_screen,
        iconId = R.drawable.ic_home,
        iconFilledId = R.drawable.ic_home_filled
    ),

    LIBRARY(
        route = LIBRARY_ROUTE,
        textId = R.string.library_screen,
        iconId = R.drawable.ic_settings,
        iconFilledId = R.drawable.ic_settings_filled
    )
}
