package com.tyom.feature_main.viewmodel

import android.bluetooth.BluetoothDevice
import com.tyom.feature_main.models.BottomNavigationItem
import com.example.feature_home.models.Note
import com.example.feature_home.models.SettingsState
import com.tyom.core_ui.interfaces.UIState


data class MainUIState(
    val settingsState: SettingsState = SettingsState(),
    val bottomItems: List<BottomNavigationItem> = emptyList(),
    val bluetoothDevices: List<BluetoothDevice?> = emptyList(),

    val currentNotes: List<Note> = emptyList(),
    val liveNotes: List<Pair<List<Note>, Int>> = emptyList()
) : UIState