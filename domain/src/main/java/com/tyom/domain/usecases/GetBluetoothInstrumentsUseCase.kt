package com.tyom.domain.usecases

import com.tyom.domain.models.Instrument
import com.tyom.domain.repository.InitRepository
import javax.inject.Inject

class GetBluetoothInstrumentsUseCase @Inject constructor(
    private val initRepository: InitRepository,
) {
    suspend fun execute(): List<Instrument> {
        return initRepository.getBluetoothInstruments()
    }
}