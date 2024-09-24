package com.tyom.domain.models

import android.bluetooth.BluetoothDevice
import com.tyom.utils.extensions.empty

data class Instrument(
    val name: String,
    val address: String
)

fun String?.toInstrument(): Instrument? {
    return if (this.isNullOrEmpty()) null
    else Instrument(
        name = this,
        address = String.empty()
    )
}

fun BluetoothDevice?.toInstrument(): Instrument {
    return Instrument(
        name = this?.name.orEmpty(),
        address = this?.address.orEmpty()
    )
}
