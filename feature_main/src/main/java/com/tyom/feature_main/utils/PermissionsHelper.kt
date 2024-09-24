package com.tyom.feature_main.utils

import android.Manifest
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
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import androidx.core.content.ContextCompat

fun hasBluetoothPermissions(context: Context): Boolean {
    val permissionsBluetooth = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> arrayOf(
            BLUETOOTH_SCAN,
            BLUETOOTH_ADVERTISE,
            BLUETOOTH_CONNECT
        )

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> arrayOf(
            BLUETOOTH,
            BLUETOOTH_ADMIN
        )

        else -> arrayOf(
            BLUETOOTH,
            BLUETOOTH_ADMIN
        )
    }

    return permissionsBluetooth.all { permission ->
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}

fun hasLocationPermissions(context: Context): Boolean {
    val permissionsLocation = arrayOf(
        ACCESS_COARSE_LOCATION,
        ACCESS_FINE_LOCATION
    )

    return permissionsLocation.all { permission ->
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}

fun hasStoragePermissions(context: Context): Boolean {
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
            val permissionsMedia = arrayOf(
                READ_MEDIA_IMAGES,
                READ_MEDIA_VIDEO
            )
            permissionsMedia.all { permission ->
                ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            }
        }

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
            if (Environment.isExternalStorageManager()) {
                true
            } else {
                val permissionsStandard = arrayOf(
                    READ_EXTERNAL_STORAGE
                )
                permissionsStandard.all { permission ->
                    ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) == PackageManager.PERMISSION_GRANTED
                }
            }
        }

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
            ContextCompat.checkSelfPermission(
                context,
                READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }

        else -> {
            val permissionsStandard = arrayOf(
                WRITE_EXTERNAL_STORAGE,
                READ_EXTERNAL_STORAGE
            )
            permissionsStandard.all { permission ->
                ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            }
        }
    }
}