package com.tyom.feature_main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.tyom.feature_main.viewmodel.MainViewModel
import com.tyom.feature_main.ui.theme.NoteStudioTheme
import com.tyom.feature_main.ui.views.NavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val state = mainViewModel.uiState.collectAsStateWithLifecycle().value
            val navController = rememberNavController()

            NoteStudioTheme {
                NavGraph(
                    navController = navController,
                    state = state
                )
            }
        }
    }
}
