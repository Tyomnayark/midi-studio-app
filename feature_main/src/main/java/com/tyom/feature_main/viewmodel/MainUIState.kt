package com.tyom.feature_main.viewmodel

import android.bluetooth.BluetoothDevice
import android.media.midi.MidiDeviceInfo
import com.example.feature_record.models.SettingsState
import com.tyom.core_ui.models.PianoConfiguration
import com.tyom.domain.models.Note
import com.tyom.feature_main.models.BottomNavigationItem


data class MainUIState(
    val settingsState: SettingsState = SettingsState(),
    val pianoConfiguration: PianoConfiguration = PianoConfiguration(),
    val bottomItems: List<BottomNavigationItem> = emptyList(),

    val bluetoothDevices: List<BluetoothDevice?> = emptyList(),
    val midiDevices: List<MidiDeviceInfo?> = emptyList(),

    val currentNotes: List<Note> = emptyList(),
    val liveNotes: Map<Int, List<Note>> = emptyMap(),
    val mapSize: Int = 0,
    val lastTimeStamp: Long = 0
)