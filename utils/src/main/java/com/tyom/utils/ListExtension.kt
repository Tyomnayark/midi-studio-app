package com.tyom.utils

fun List<Any>.ifNotEmpty(func: () -> Unit) =
    if (this.isNotEmpty()) {
        func()
    } else {
        Unit
    }

fun List<Any>.ifEmpty(func: () -> Unit) =
    if (this.isEmpty()) {
        func()
    } else {
        Unit
    }