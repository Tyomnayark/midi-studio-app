package com.tyom.data.repository

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.media.midi.MidiDeviceInfo
import android.media.midi.MidiReceiver
import com.tyom.data.Preferences
import com.tyom.data.PrefsDictionary.INSTRUMENT_BLUETOOTH
import com.tyom.data.PrefsDictionary.INSTRUMENT_MIDI
import com.tyom.data.providers.MidiProvider
import com.tyom.domain.models.Instrument
import com.tyom.domain.models.toInstrument
import com.tyom.domain.repository.MIDIRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MIDIRepositoryImpl @Inject constructor(
    @ApplicationContext private var context: Context,
    private var midiProvider: MidiProvider
) : MIDIRepository {
    override suspend fun checkHaveConnectedInstrumentBluetooth(): Instrument? =
        Preferences.getString(context, INSTRUMENT_BLUETOOTH)?.toInstrument()

    override suspend fun checkHaveConnectedInstrumentMidi(): Instrument? =
        Preferences.getString(context, INSTRUMENT_MIDI)?.toInstrument()

    override suspend fun scanBluetoothInstruments(): List<BluetoothDevice?> {
        return midiProvider.scanBluetoothInstruments()
    }

    override suspend fun scanMidiInstruments(): List<MidiDeviceInfo?> {
        return midiProvider.scanMidiInstruments()
    }

    override suspend fun connectBluetoothInstrument(
        bluetoothDevice: BluetoothDevice,
        receiver: MidiReceiver
    ): Boolean {
        return midiProvider.connectBluetoothDevice(bluetoothDevice, receiver)
    }

    override suspend fun connectMidiInstrument(
        midiDeviceInfo: MidiDeviceInfo,
        receiver: MidiReceiver
    ): Boolean {
        return midiProvider.connectMidiDevice(midiDeviceInfo, receiver)
    }

    override suspend fun addInstrumentBluetoothToPreferences(instrument: Instrument) {
        Preferences.putString(context, INSTRUMENT_BLUETOOTH, instrument.name)
    }

    override suspend fun addInstrumentMidiToPreferences(instrument: Instrument) {
        Preferences.putString(context, INSTRUMENT_MIDI, instrument.name)
    }

}