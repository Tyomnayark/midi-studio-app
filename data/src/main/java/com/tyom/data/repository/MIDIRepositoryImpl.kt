package com.tyom.data.repository

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.media.midi.MidiReceiver
import com.tyom.data.Preferences
import com.tyom.data.PrefsDictionary.INSTRUMENT
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
    override suspend fun checkHaveConnectedInstrument(): Instrument? =
        Preferences.getString(context, INSTRUMENT)?.toInstrument()

    override suspend fun scanMidiInstruments(): List<BluetoothDevice?> {
        return  midiProvider.scanMidiInstruments()
    }

    override suspend fun connectBluetoothInstrument( bluetoothDevice: BluetoothDevice, receiver: MidiReceiver): Boolean {
        return midiProvider.connectBluetoothDevice(bluetoothDevice, receiver)
    }

    override suspend fun addInstrumentToPreferences(instrument: Instrument) {
        Preferences.putString(context, INSTRUMENT, instrument.name)
    }
}