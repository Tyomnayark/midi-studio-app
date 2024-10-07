package com.tyom.domain.usecases

import com.tyom.domain.models.Instrument
import com.tyom.domain.repository.MIDIRepository
import javax.inject.Inject

class AddInstrumentMidiToPreferencesUseCase @Inject constructor(
    private val midiRepository: MIDIRepository
) {
    suspend fun execute(instrument: Instrument) {
        return midiRepository.addInstrumentMidiToPreferences(instrument)
    }
}