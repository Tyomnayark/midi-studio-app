package com.tyom.feature_main

import android.Manifest
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
import com.tyom.feature_main.ui.theme.NoteStudioTheme
import com.tyom.feature_main.ui.views.BottomBar
import com.tyom.feature_main.ui.views.NavGraph
import com.tyom.feature_main.utils.hasBluetoothPermissions
import com.tyom.feature_main.utils.hasLocationPermissions
import com.tyom.feature_main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


private const val REQUEST_CODE_BLUETOOTH_PERMISSIONS = 1001
private const val REQUEST_CODE_LOCATION_PERMISSIONS = 1002

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!hasBluetoothPermissions(this) && !hasLocationPermissions(this)) {
            requestBluetoothPermissions()
        }
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

    private fun requestBluetoothPermissions() {
        val permissionsBluetooth = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> arrayOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.BLUETOOTH_CONNECT
            )

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN
            )

            else -> arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN
            )
        }

        ActivityCompat.requestPermissions(
            this,
            permissionsBluetooth,
            REQUEST_CODE_BLUETOOTH_PERMISSIONS
        )
    }

    private fun requestLocationPermissions() {
        val permissionsLocation = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        ActivityCompat.requestPermissions(
            this,
            permissionsLocation,
            REQUEST_CODE_LOCATION_PERMISSIONS
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_BLUETOOTH_PERMISSIONS -> {
                requestLocationPermissions()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}