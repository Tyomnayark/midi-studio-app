package com.tyom.domain.models

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.media.midi.MidiDeviceInfo
import com.tyom.core_utils.extensions.empty

data class Instrument(
    val name: String,
    val address: String,
    val id: Int = 0
)

fun String?.toInstrument(): Instrument? {
    return if (this.isNullOrEmpty()) null
    else Instrument(
        name = this,
        address = String.empty()
    )
}

@SuppressLint("MissingPermission")
fun BluetoothDevice?.toInstrument(): Instrument {
    return Instrument(
        name = this?.name.orEmpty(),
        address = this?.address.orEmpty()
    )
}

fun MidiDeviceInfo?.toInstrument(): Instrument {
    return Instrument(
        name = this?.properties?.getString(MidiDeviceInfo.PROPERTY_NAME).orEmpty(),
        address = String.empty(),
        id = this?.id ?: 0
    )

}