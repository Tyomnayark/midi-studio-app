package com.tyom.domain.usecases

import android.bluetooth.BluetoothDevice
import android.media.midi.MidiReceiver
import com.tyom.domain.repository.MIDIRepository
import javax.inject.Inject

class ConnectBluetoothDeviceUseCase @Inject constructor(
    private val midiRepository: MIDIRepository
) {
    suspend fun execute(bluetoothDevice: BluetoothDevice, receiver: MidiReceiver): Boolean {
        return midiRepository.connectBluetoothInstrument(bluetoothDevice, receiver)
    }
}