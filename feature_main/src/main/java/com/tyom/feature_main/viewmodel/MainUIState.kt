package com.tyom.feature_main.viewmodel

import android.bluetooth.BluetoothDevice
import com.tyom.domain.models.Instrument
import com.tyom.feature_main.models.BottomNavigationItem
import com.tyom.feature_main.models.Note
import com.tyom.feature_main.models.SettingsState
import com.tyom.ui_tools.interfaces.UIState


data class MainUIState(
    val settingsState: SettingsState = SettingsState(),
    val bottomItems: List<BottomNavigationItem> = emptyList(),
    val bluetoothDevices: List<BluetoothDevice?> = emptyList(),

    val currentNotes: List<Note> = emptyList(),
    val liveNotes: List<Pair<List<Note>, Int>> = emptyList()
) : UIState