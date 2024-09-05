package com.tyom.feature_main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tyom.feature_main.ui.theme.NoteStudioTheme
import com.tyom.feature_main.ui.views.BottomBar
import com.tyom.feature_main.ui.views.NavGraph
import com.tyom.feature_main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            val state = mainViewModel.uiState.collectAsStateWithLifecycle().value
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentScreen by remember(backStackEntry?.destination?.route) {
                derivedStateOf {
                    backStackEntry?.destination?.route
                }
            }

            NoteStudioTheme {
                Scaffold(
                    bottomBar = {
                        BottomBar(
                            state.bottomItems,
                            currentScreen = currentScreen,
                            onClickBottomItem = { item ->
                                navController.popBackStack(item.screen.route, inclusive = false)
                                navController.navigate(item.screen.route) {
                                    navController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                mainViewModel.onClickBottomItem(item)
                            }
                        )
                    },
                ) {
                    NavGraph(
                        navController = navController,
                        state = state,
                        viewModel = mainViewModel
                    )
                }
            }
        }
    }
}
