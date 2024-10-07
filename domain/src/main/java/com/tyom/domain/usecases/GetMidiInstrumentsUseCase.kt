package com.tyom.domain.usecases

import android.media.midi.MidiDeviceInfo
import com.tyom.domain.repository.MIDIRepository
import javax.inject.Inject

class GetMidiInstrumentsUseCase @Inject constructor(
    private val midiRepository: MIDIRepository
) {
    suspend fun execute(): List<MidiDeviceInfo?> = midiRepository.scanMidiInstruments()
}