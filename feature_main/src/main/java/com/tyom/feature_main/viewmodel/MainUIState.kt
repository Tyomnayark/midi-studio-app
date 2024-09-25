package com.tyom.feature_main.viewmodel

import android.bluetooth.BluetoothDevice
import com.example.feature_home.models.SettingsState
import com.tyom.core_ui.models.Note
import com.tyom.core_ui.models.PianoConfiguration
import com.tyom.feature_main.models.BottomNavigationItem


data class MainUIState(
    val settingsState: SettingsState = SettingsState(),
    val pianoConfiguration: PianoConfiguration = PianoConfiguration(),
    val bottomItems: List<BottomNavigationItem> = emptyList(),
    val bluetoothDevices: List<BluetoothDevice?> = emptyList(),

    val currentNotes: List<Note> = emptyList(),
    val liveNotes: List<Pair<List<Note>, Int>> = emptyList()
)