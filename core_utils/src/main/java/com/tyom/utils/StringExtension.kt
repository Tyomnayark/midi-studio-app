package com.tyom.utils

val CharSequence?.orEmpty get() = this ?: ""

fun String.Companion.empty(): String = ""
