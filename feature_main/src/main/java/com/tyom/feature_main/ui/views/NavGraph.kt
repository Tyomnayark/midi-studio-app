package com.tyom.feature_main.ui.views

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tyom.feature_main.models.Routes.HOME_ROUTE
import com.tyom.feature_main.models.Routes.SETTINGS_ROUTE
import com.tyom.feature_main.ui.views.home.HomePage
import com.tyom.feature_main.viewmodel.MainUIState
import com.tyom.feature_main.viewmodel.MainViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    state: MainUIState,
    viewModel: MainViewModel
) {
    NavHost(navController = navController, startDestination = HOME_ROUTE) {
        composable(HOME_ROUTE) {
            HomePage(
                isKeyboardVisible = state.isKeyboardVisible,
                instruments = state.instruments,
                notes = state.currentNotes,
                liveNotes = state.liveNotes,
                onClickRefreshInstruments = {
                    viewModel.onClickRefreshInstruments()
                },
                onClickSelectInstrument = { instrument ->
                    viewModel.onClickSelectInstrument(instrument)
                }
            )
        }
        composable(SETTINGS_ROUTE) {
            // TODO:  @Tyom [8/26/24] { добавить позже }
        }
    }
}