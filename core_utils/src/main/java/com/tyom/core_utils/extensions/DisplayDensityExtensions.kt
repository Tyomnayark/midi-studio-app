package com.tyom.core_utils.extensions

import android.content.res.Resources

/**
 * Преобразует значение из dp в px.
 */
val Int.toDp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Преобразует значение из sp в px.
 */
val Int.toSp: Int
    get() = (this * Resources.getSystem().displayMetrics.scaledDensity).toInt()