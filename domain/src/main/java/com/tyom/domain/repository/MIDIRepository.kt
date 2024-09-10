package com.tyom.domain.repository

import android.bluetooth.BluetoothDevice
import android.media.midi.MidiReceiver
import com.tyom.domain.models.Instrument

interface MIDIRepository {
    suspend fun checkHaveConnectedInstrument(): Instrument?

    suspend fun scanMidiInstruments(): List<BluetoothDevice?>

    suspend fun getWiredInstruments(): List<Instrument>

    suspend fun connectBluetoothInstrument(bluetoothDevice: BluetoothDevice, receiver: MidiReceiver)

}