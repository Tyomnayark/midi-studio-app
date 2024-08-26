package com.tyom.notestudio.ui.views

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.tyom.notestudio.ui.views.Routes.HOME_ROUTE
import androidx.navigation.compose.composable
import com.tyom.notestudio.interfaces.UIState
import com.tyom.notestudio.ui.views.Routes.SETTINGS_ROUTE
import com.tyom.notestudio.ui.views.home.HomePage

object Routes {
    const val HOME_ROUTE = "home"
    const val SETTINGS_ROUTE = "settings"
}

@Composable
fun NavGraph(navController: NavHostController, state: UIState) {
    NavHost(navController = navController, startDestination = HOME_ROUTE) {
        composable(HOME_ROUTE) {
            HomePage()
        }
        composable(SETTINGS_ROUTE) {
            // TODO:  @Tyom [8/26/24] { добавить позже }
        }
    }
}