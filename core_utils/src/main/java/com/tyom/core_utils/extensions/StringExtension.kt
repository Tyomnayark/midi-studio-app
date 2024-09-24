package com.tyom.core_utils.extensions

val CharSequence?.orEmpty get() = this ?: ""

fun String.Companion.empty(): String = ""
