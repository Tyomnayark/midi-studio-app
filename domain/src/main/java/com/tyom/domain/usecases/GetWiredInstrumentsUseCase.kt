package com.tyom.domain.usecases

import com.tyom.domain.models.Instrument
import com.tyom.domain.repository.MIDIRepository
import javax.inject.Inject

class GetWiredInstrumentsUseCase @Inject constructor(
    private val midiRepository: MIDIRepository
) {
    suspend fun execute(): List<Instrument> {
        return midiRepository.getWiredInstruments()
    }
}