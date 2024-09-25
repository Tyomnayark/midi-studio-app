package com.tyom.feature_main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.BLUETOOTH
import android.Manifest.permission.BLUETOOTH_ADMIN
import android.Manifest.permission.BLUETOOTH_ADVERTISE
import android.Manifest.permission.BLUETOOTH_CONNECT
import android.Manifest.permission.BLUETOOTH_SCAN
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tyom.core_ui.theme.NoteStudioTheme
import com.tyom.feature_main.ui.views.BottomBar
import com.tyom.feature_main.ui.views.NavGraph
import com.tyom.core_utils.utils.hasBluetoothPermissions
import com.tyom.core_utils.utils.hasLocationPermissions
import com.tyom.core_utils.utils.hasStoragePermissions
import com.tyom.feature_main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


private const val REQUEST_PERMISSION_CODE = 1001

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!hasBluetoothPermissions(this) ||
            !hasLocationPermissions(this) ||
            !hasStoragePermissions(this)
        ) {
            requestPermissions()
        }
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

    private fun requestPermissions() {
        val permissions = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> arrayOf(
                BLUETOOTH_SCAN,
                BLUETOOTH_ADVERTISE,
                BLUETOOTH_CONNECT,
                READ_MEDIA_IMAGES,
                READ_MEDIA_VIDEO,
                ACCESS_COARSE_LOCATION,
                ACCESS_FINE_LOCATION
            )

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> arrayOf(
                BLUETOOTH_SCAN,
                BLUETOOTH_ADVERTISE,
                BLUETOOTH_CONNECT,
                WRITE_EXTERNAL_STORAGE,
                READ_EXTERNAL_STORAGE,
                ACCESS_COARSE_LOCATION,
                ACCESS_FINE_LOCATION
            )

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> arrayOf(
                BLUETOOTH,
                BLUETOOTH_ADMIN,
                ACCESS_COARSE_LOCATION,
                ACCESS_FINE_LOCATION,
                READ_EXTERNAL_STORAGE
            )

            else -> arrayOf(
                BLUETOOTH,
                BLUETOOTH_ADMIN,
                ACCESS_COARSE_LOCATION,
                ACCESS_FINE_LOCATION,
                WRITE_EXTERNAL_STORAGE,
                READ_EXTERNAL_STORAGE
            )
        }

        ActivityCompat.requestPermissions(
            this,
            permissions,
            REQUEST_PERMISSION_CODE
        )
    }

}