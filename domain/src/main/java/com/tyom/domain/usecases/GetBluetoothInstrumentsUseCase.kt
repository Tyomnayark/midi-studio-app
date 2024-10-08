package com.tyom.domain.usecases

import android.bluetooth.BluetoothDevice
import com.tyom.domain.repository.MIDIRepository
import javax.inject.Inject

class GetBluetoothInstrumentsUseCase  @Inject constructor(
    private val midiRepository: MIDIRepository
) {
    suspend fun execute(): List<BluetoothDevice?> {
        return midiRepository.scanBluetoothInstruments()
    }
}