package com.tyom.feature_main.ui.views

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.feature_home.ui.HomePage
import com.tyom.feature_library.ui.LibraryPage
import com.tyom.feature_main.models.Routes.HOME_ROUTE
import com.tyom.feature_main.models.Routes.LIBRARY_ROUTE
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
                pianoConfiguration = state.pianoConfiguration,
                settingsState = state.settingsState,
                notes = state.currentNotes,
                liveNotes = state.liveNotes,

                onClickRefreshInstruments = {
                    viewModel.onClickRefreshInstruments()
                },
                onClickSelectInstrument = { instrument ->
                    viewModel.onClickSelectInstrument(instrument)
                },
                onClickChangeKeyboardVisibility = {
                    viewModel.changeKeyboardVisibility()
                },
                onClickChangeAutoConnect = {
                    viewModel.changeAutoConnect()
                }
            )
        }
        composable(LIBRARY_ROUTE) {
            LibraryPage()
        }
    }
}