package com.example.feature_home.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.feature_home.R
import com.example.feature_home.constants.BottomLineNotesList
import com.example.feature_home.constants.NeedLineNotesMap
import com.example.feature_home.constants.NoteConstants.A0
import com.example.feature_home.constants.NoteConstants.A2
import com.example.feature_home.constants.NoteConstants.A3
import com.example.feature_home.constants.NoteConstants.B3
import com.example.feature_home.constants.NoteConstants.C1
import com.example.feature_home.constants.NoteConstants.C4
import com.example.feature_home.constants.NoteConstants.C8
import com.example.feature_home.constants.NoteConstants.D3
import com.example.feature_home.constants.NoteConstants.D4
import com.example.feature_home.constants.NoteConstants.D7
import com.example.feature_home.constants.NoteConstants.E5
import com.example.feature_home.constants.NoteConstants.F4
import com.example.feature_home.models.Note
import com.tyom.core_ui.extensions.FigmaLargePreview
import com.tyom.core_ui.utils.drawableToImageBitmap

private const val LINE_COUNT = 5
private const val LINE_SPACING = 20f
private const val HALF_LINE_SPACING = LINE_SPACING / 2
private const val TOP_PADDING = 0f
private const val STROKE_WIDTH = 4f
private const val NOTE_COUNT = 11
private const val PADDING = LINE_SPACING * 5
private const val PADDING_FOR_BOTTOM_LINE = LINE_SPACING * 2
private const val NOTE_PADDING_TOP = PADDING - (LINE_SPACING * 1.5f)
private const val NOTE_PADDING_BOTTOM = PADDING_FOR_BOTTOM_LINE - (LINE_SPACING * 1.5f)

@SuppressLint("UseCompatLoadingForDrawables")
@Composable
fun LiveNoteString(
    modifier: Modifier = Modifier,
    liveNotes: List<Pair<List<Note>, Int>>
) {
    val context = LocalContext.current

    val drawableTrebleClef = context.getDrawable(com.tyom.core_ui.R.drawable.treble_clef)
    val drawableBassClef = context.getDrawable(com.tyom.core_ui.R.drawable.bass_clef)

    val widthFromStrokes = LINE_SPACING * 31
    val width = (widthFromStrokes / LocalDensity.current.density).dp
    val bassLinesStartX = HALF_LINE_SPACING * 5 + PADDING
    val topLinesStartX = (HALF_LINE_SPACING * 23) + PADDING

    Box(
        modifier = Modifier
            .background(
                color = Color.White,
                shape =  RoundedCornerShape(LINE_SPACING.dp)
            )
    ) {
        Canvas(
            modifier = modifier
                .padding(vertical = LINE_SPACING.dp)
                .fillMaxHeight()
                .width(width)
        ) {
            // bass lines
            drawMusicLines(
                startX = bassLinesStartX,
                lineCount = LINE_COUNT,
                lineColor = Color.Black
            )

            // top lines
            drawMusicLines(
                startX = topLinesStartX,
                lineCount = LINE_COUNT,
                lineColor = Color.Black
            )

            // edges
            drawLine(
                color = Color.Black,
                start = Offset(bassLinesStartX, 0f),
                end = Offset(topLinesStartX + LINE_SPACING * 4, 0f),
                strokeWidth = STROKE_WIDTH
            )

            drawLine(
                color = Color.Black,
                start = Offset(bassLinesStartX, size.height),
                end = Offset(topLinesStartX + LINE_SPACING * 4, size.height),
                strokeWidth = STROKE_WIDTH
            )

            // clefs
            drawableToImageBitmap(
                drawable = drawableTrebleClef,
                height = (LINE_SPACING * 12).toInt(),
                width = (LINE_SPACING * 9).toInt(),
                rotationDegrees = 90f
            )?.let { bitmap ->
                drawImage(
                    image = bitmap,
                    topLeft = Offset(topLinesStartX - LINE_SPACING * 2, -LINE_SPACING * 3)
                )
            }

            drawableToImageBitmap(
                drawable = drawableBassClef,
                height = (LINE_SPACING * 9).toInt(),
                width = (LINE_SPACING * 5).toInt(),
                rotationDegrees = 90f
            )?.let { bitmap ->
                drawImage(
                    image = bitmap,
                    topLeft = Offset(bassLinesStartX, -LINE_SPACING)
                )
            }

            // notes
            drawLiveNotes(
                liveNotes = liveNotes,
            )
        }
    }
}

fun DrawScope.drawMusicLines(startX: Float, lineCount: Int, lineColor: Color) {
    val startY = TOP_PADDING
    val endY = size.height
    for (i in 0 until lineCount) {
        val xOffset = startX + i * LINE_SPACING
        drawLine(
            color = lineColor,
            start = Offset(xOffset, startY),
            end = Offset(xOffset, endY),
            strokeWidth = STROKE_WIDTH
        )
    }
}

fun DrawScope.drawLiveNotes(
    liveNotes: List<Pair<List<Note>, Int>>
) {
    liveNotes.forEach { (notes, timeMoment) ->

        notes.forEach { note ->
            val cordX =
                note.value * HALF_LINE_SPACING + if (note.value > A3) NOTE_PADDING_TOP else NOTE_PADDING_BOTTOM
            val cordY = timeMoment * (size.height / NOTE_COUNT)
            val isNeedAddLine = NeedLineNotesMap.contains(note.value)
            if (isNeedAddLine) {
                val lineCordX = if (note.value % 2 == 1) {
                    cordX + HALF_LINE_SPACING
                } else {
                    cordX + LINE_SPACING
                }
                val lineCount = NeedLineNotesMap[note.value] ?: 0
                val isBottomLines = BottomLineNotesList.contains(note.value)
                for (i in 0 until lineCount) {
                    val lineFinalCordX = if (isBottomLines) {
                        lineCordX + (LINE_SPACING * i)
                    } else {
                        lineCordX - (LINE_SPACING * i)
                    }
                    drawLine(
                        start = Offset(lineFinalCordX, cordY + 37f),
                        end = Offset(lineFinalCordX, cordY - 7f),
                        strokeWidth = STROKE_WIDTH,
                        color = Color.Black,
                    )
                }
            }

            drawOval(
                color = Color.Black,
                size = Size(LINE_SPACING, 30f),
                topLeft = Offset(
                    x = cordX,
                    y = cordY
                )
            )
        }
    }
}

@FigmaLargePreview
@Composable
fun LiveNoteStringPreview() {
    val liveNotes = listOf(
        listOf(Note(value = C4, isWhiteKey = true)) to 1,
        listOf(
            Note(value = B3, isWhiteKey = true),
            Note(value = A3, isWhiteKey = true),
            Note(value = D3, isWhiteKey = true),
        ) to 2,
        listOf(
            Note(value = A3, isWhiteKey = true),
            Note(value = A2, isWhiteKey = true),
            Note(value = D4, isWhiteKey = true),
            Note(value = F4, isWhiteKey = true),
            Note(value = E5, isWhiteKey = true),
            Note(value = D7, isWhiteKey = true),
        ) to 3,
        listOf(
            Note(value = C8, isWhiteKey = true),
            Note(value = A0, isWhiteKey = true),
            Note(value = C1, isWhiteKey = true),

            ) to 4,
        listOf(
            Note(value = 9, isWhiteKey = true),
            Note(value = 4, isWhiteKey = true)
        ) to 5,
        listOf(
            Note(value = 10, isWhiteKey = true),
            Note(value = 18, isWhiteKey = true)
        ) to 6,
        listOf(
            Note(value = 19, isWhiteKey = true),
            Note(value = 25, isWhiteKey = true)
        ) to 7,
        listOf(
            Note(value = 21, isWhiteKey = true),
            Note(value = 30, isWhiteKey = true)
        ) to 8,
        listOf(
            Note(value = 22, isWhiteKey = true),
            Note(value = 20, isWhiteKey = true)
        ) to 9,
        listOf(
            Note(value = 9, isWhiteKey = true),
            Note(value = 0, isWhiteKey = true)
        ) to 10
    )
    LiveNoteString(
        liveNotes = liveNotes
    )
}