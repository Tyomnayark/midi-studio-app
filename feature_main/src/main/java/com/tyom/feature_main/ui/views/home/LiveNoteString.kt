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
import com.tyom.feature_main.models.Note
import com.tyom.ui_tools.extensions.FigmaLargePreview

private const val LINE_COUNT = 5
private const val LINE_SPACING = 20f
private const val START_PADDING_COEFF = 2.5f
private const val TOP_PADDING = 0f
private const val STROKE_WIDTH = 4f

@Composable
fun LiveNoteString(
    modifier: Modifier = Modifier,
    liveNotes: List<Pair<List<Note>, Int>>
) {
    Canvas(
        modifier = modifier
            .fillMaxHeight()
            .width(dimensionResource(R.dimen._120dp))
    ) {
        drawMusicLines(
            lineCount = LINE_COUNT,
            lineColor = Color.Black,
            lineSpacing = LINE_SPACING
        )

        drawLiveNotes(
            liveNotes = liveNotes,
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

fun DrawScope.drawLiveNotes(
    liveNotes: List<Pair<List<Note>, Int>>,
    lineSpacing: Float
) {
    val startY = TOP_PADDING
    val startX = size.width / START_PADDING_COEFF

    val noteToLineOffset = mapOf(
        0 to 0f,  // map note 0 to first line, etc.
        1 to lineSpacing,
        2 to lineSpacing * 2,
        3 to lineSpacing * 3,
        4 to lineSpacing * 4
    )

    // Iterate through the live notes and draw each note on its corresponding line
    liveNotes.forEach { (notes, timeMoment) ->
        // Calculate the X position based on the time moment
        val xOffset = startX + timeMoment * (size.width / 10)  // assuming 10 moments

        notes.forEach { note ->
            // Find the Y position based on the note value
            val yOffset = noteToLineOffset[note.value] ?: 0f
            // Draw the note as a small circle or any other shape
            drawCircle(
                color = Color.Red,  // Change color as needed
                center = Offset(xOffset, startY + yOffset),
                radius = 10f  // Adjust radius for note size
            )
        }
    }
}

@FigmaLargePreview
@Composable
fun LiveNoteStringPreview() {
    LiveNoteString(
        liveNotes = emptyList()
    )
}