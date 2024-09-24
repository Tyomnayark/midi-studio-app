package com.tyom.core_ui.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

fun drawableToImageBitmap(
    drawable: Drawable?,
    height: Int = drawable?.intrinsicHeight ?: 1,
    width: Int = drawable?.intrinsicWidth ?: 1,
    rotationDegrees: Float = 0f
): ImageBitmap? {
    if (drawable == null) return null

    if (drawable is BitmapDrawable) {
        drawable.bitmap?.let { return it.asImageBitmap() }
    }

    val bitmap: Bitmap = if (width > 0 && height > 0) {
        Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    } else {
        Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }

    val canvas = Canvas(bitmap)

    val matrix = Matrix()
    matrix.setRotate(rotationDegrees, (canvas.width / 2).toFloat(), (canvas.height / 2).toFloat())
    canvas.setMatrix(matrix)

    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap.asImageBitmap()
}