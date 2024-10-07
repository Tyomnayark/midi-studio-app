package com.tyom.domain.usecases

import android.media.midi.MidiDeviceInfo
import android.media.midi.MidiReceiver
import com.tyom.domain.repository.MIDIRepository
import javax.inject.Inject

class ConnectMidiDeviceUseCase @Inject constructor(
    private val midiRepository: MIDIRepository
) {
    suspend fun execute(midiDeviceInfo: MidiDeviceInfo, receiver: MidiReceiver): Boolean =
        midiRepository.connectMidiInstrument(midiDeviceInfo, receiver)

}