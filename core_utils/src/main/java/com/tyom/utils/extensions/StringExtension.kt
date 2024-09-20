package com.tyom.utils.extensions

val CharSequence?.orEmpty get() = this ?: ""

fun String.Companion.empty(): String = ""
