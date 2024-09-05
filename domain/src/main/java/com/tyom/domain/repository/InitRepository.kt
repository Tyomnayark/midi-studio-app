package com.tyom.domain.repository

import com.tyom.domain.models.Instrument

interface InitRepository {
    suspend fun checkHaveConnectedInstrument(): Instrument?

    suspend fun getBluetoothInstruments(): List<Instrument>

    suspend fun getWiredInstruments(): List<Instrument>
}