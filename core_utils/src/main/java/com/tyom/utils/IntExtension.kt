package com.tyom.utils

import android.content.Context
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

val Int?.orZero get() = this ?: 0

val Int?.orOne get() = this ?: 1

val Int?.isZero get() = this?.let { it == 0 }.orFalse

val Int?.isOne get() = this?.let { it == 1 }.orFalse

val Int?.isNotZero get() = this?.let { it != 0 }.orFalse

/**
 * Перевод dp в px
 * */
fun Int.dpToPx(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this * density).roundToInt()
}

/**
 * Перевод px в dp
 * */
fun Int.pxToDp(context: Context): Dp {
    val density = context.resources.displayMetrics.density
    return (this / density).roundToInt().dp
}
