package com.tyom.domain.repository

import android.bluetooth.BluetoothDevice
import android.media.midi.MidiDeviceInfo
import android.media.midi.MidiReceiver
import com.tyom.domain.models.Instrument

interface MIDIRepository {
    suspend fun checkHaveConnectedInstrumentBluetooth(): Instrument?

    suspend fun checkHaveConnectedInstrumentMidi(): Instrument?

    suspend fun scanBluetoothInstruments(): List<BluetoothDevice?>

    suspend fun scanMidiInstruments(): List<MidiDeviceInfo?>

    suspend fun connectBluetoothInstrument(bluetoothDevice: BluetoothDevice, receiver: MidiReceiver): Boolean

    suspend fun connectMidiInstrument(midiDeviceInfo: MidiDeviceInfo, receiver: MidiReceiver): Boolean

    suspend fun addInstrumentBluetoothToPreferences(instrument: Instrument)

    suspend fun addInstrumentMidiToPreferences(instrument: Instrument)

}