package com.tyom.feature_main.ui.views.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.dimensionResource
import com.tyom.feature_main.R
import com.tyom.ui_tools.extensions.FigmaLargePreview

private const val LINE_COUNT = 5
private const val LINE_SPACING = 20f
private const val START_PADDING_COEFF = 2.5f
private const val TOP_PADDING = 0f
private const val STROKE_WIDTH = 4f

@Composable
fun LiveNoteString(
        liveNotes: List<Pair<List<Int>, Int>>
) {
    Canvas(
        modifier = Modifier
            .fillMaxHeight()
            .width(dimensionResource(R.dimen._120dp))
    ) {
        drawMusicLines(
            lineCount = LINE_COUNT,
            lineColor = Color.Black,
            lineSpacing = LINE_SPACING
        )
    }
}

fun DrawScope.drawMusicLines(lineCount: Int, lineColor: Color, lineSpacing: Float) {
    val startY = TOP_PADDING
    val endY = size.height
    val startX = size.width / START_PADDING_COEFF
    for (i in 0 until lineCount) {
        val xOffset = startX + i * lineSpacing
        drawLine(
            color = lineColor,
            start = Offset(xOffset, startY),
            end = Offset(xOffset, endY),
            strokeWidth = STROKE_WIDTH
        )
    }
}

@FigmaLargePreview
@Composable
fun LiveNoteStringPreview() {
    LiveNoteString(
        liveNotes = emptyList()
    )
}