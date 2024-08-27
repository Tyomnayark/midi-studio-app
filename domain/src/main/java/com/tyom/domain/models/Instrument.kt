package com.tyom.domain.models

data class Instrument(
    val name: String
)

fun String?.toInstrument(): Instrument {
    return Instrument(name = this.orEmpty())
}
