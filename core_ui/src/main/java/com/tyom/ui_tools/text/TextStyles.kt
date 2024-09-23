package com.tyom.ui_tools.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp


@Composable
fun settingsItemFont(
    size: Dp,
    color: Color = Color.Black,
): TextStyle {
    val fontSize = with(LocalDensity.current) { size.toSp() }

    return TextStyle(
        fontSize = fontSize,
        color = color
    )
}