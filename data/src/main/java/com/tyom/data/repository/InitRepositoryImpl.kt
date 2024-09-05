package com.tyom.data.repository

import android.content.Context
import com.tyom.data.Preferences
import com.tyom.data.PrefsDictionary.INSTRUMENT
import com.tyom.data.midi.BluetoothMidiInstrumentProvider
import com.tyom.domain.models.Instrument
import com.tyom.domain.models.toInstrument
import com.tyom.domain.repository.InitRepository
import javax.inject.Inject

class InitRepositoryImpl @Inject constructor(
    private var context: Context,
    private val midiInstrumentProvider: BluetoothMidiInstrumentProvider
): InitRepository {
    override suspend fun checkHaveConnectedInstrument(): Instrument? {
        return Preferences.getString(context, INSTRUMENT)?.toInstrument()
    }

    override suspend fun getBluetoothInstruments(): List<Instrument> {
       return midiInstrumentProvider.getBluetoothInstruments()
    }

    override suspend fun getWiredInstruments(): List<Instrument> {
        TODO("Not yet implemented")
    }
}