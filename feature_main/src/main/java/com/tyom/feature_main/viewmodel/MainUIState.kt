package com.tyom.feature_main.viewmodel

import android.bluetooth.BluetoothDevice
import com.tyom.domain.models.Instrument
import com.tyom.feature_main.models.BottomNavigationItem
import com.tyom.feature_main.models.Note
import com.tyom.ui_tools.interfaces.UIState


data class MainUIState(
    val selectedInstrument: Instrument? = null,
    val bottomItems: List<BottomNavigationItem> = emptyList(),
    val bluetoothDevices: List<BluetoothDevice?> = emptyList(),
    val instruments: List<Instrument> = emptyList(),

    val currentNotes: List<Note> = emptyList(),
    val liveNotes: List<Pair<Note, Int>> = emptyList()
): UIState