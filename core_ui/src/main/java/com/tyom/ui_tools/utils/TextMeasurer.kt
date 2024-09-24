package com.tyom.ui_tools.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tyom.ui_tools.extensions.pxToDp
import com.tyom.utils.extensions.dpToPx
import com.tyom.utils.extensions.toDp
import kotlin.math.roundToInt

@Composable
fun getTextWidthInDp(text: String, textStyle: TextStyle): Dp {
    val textMeasurer = rememberTextMeasurer()

    val textWidth = textMeasurer.measure(
        text = text,
        style = textStyle
    ).size.width

    return (textWidth / LocalDensity.current.density).dp
}