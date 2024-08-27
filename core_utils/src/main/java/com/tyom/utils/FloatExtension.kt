package com.tyom.utils

import android.content.Context
import kotlin.math.roundToInt

val Float?.orZero get() = this ?: 0F

val Float?.orOne get() = this ?: 1F

/**
 * Перевод dp в px
 * */
fun Float.dpToPx(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this * density).roundToInt()
}