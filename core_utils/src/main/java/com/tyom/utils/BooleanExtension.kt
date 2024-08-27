package com.tyom.utils

val Boolean?.orFalse get() = this ?: false

val Boolean?.orTrue get() = this ?: true

val Boolean?.isTrue get() = this == true

fun Boolean?.ifTrue(func: () -> Unit) =
    if (this == true) {
        func()
    } else {
        Unit
    }

fun Boolean?.ifFalse(func: () -> Unit) =
    if (this == false) {
        func()
    } else {
        Unit
    }