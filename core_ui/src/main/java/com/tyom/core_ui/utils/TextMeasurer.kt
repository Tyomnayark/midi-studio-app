package com.tyom.core_ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun getTextWidthInDp(text: String, textStyle: TextStyle): Dp {
    val textMeasurer = rememberTextMeasurer()

    val textWidth = textMeasurer.measure(
        text = text,
        style = textStyle
    ).size.width

    return (textWidth / LocalDensity.current.density).dp
}