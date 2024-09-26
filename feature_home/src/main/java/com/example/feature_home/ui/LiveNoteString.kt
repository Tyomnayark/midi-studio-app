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
import androidx.compose.ui.unit.dp
import com.tyom.core_ui.extensions.FigmaLargePreview
import com.tyom.core_ui.models.PianoConfiguration
import com.tyom.core_ui.utils.drawableToImageBitmap
import com.tyom.domain.models.Note

@SuppressLint("UseCompatLoadingForDrawables")
@Composable
fun LiveNoteString(
    modifier: Modifier = Modifier,
    pianoConfiguration: PianoConfiguration,
    liveNotes: List<Pair<List<Note>, Int>>
) {
    val context = LocalContext.current

    val drawableTrebleClef = context.getDrawable(com.tyom.core_ui.R.drawable.treble_clef)
    val drawableBassClef = context.getDrawable(com.tyom.core_ui.R.drawable.bass_clef)

    val width = (pianoConfiguration.widthFromStrokes / LocalDensity.current.density).dp

    Box(
        modifier = modifier
            .background(
                color = pianoConfiguration.backgroundColor,
                shape = RoundedCornerShape(
                    topStart = pianoConfiguration.lineSpacingDp,
                    bottomStart = pianoConfiguration.lineSpacingDp
                )
            )
    ) {
        Canvas(
            modifier = Modifier
                .padding(vertical = pianoConfiguration.lineSpacingDp)
                .fillMaxHeight()
                .width(width)
        ) {
            // bass lines
            drawMusicLines(
                pianoConfiguration = pianoConfiguration,
                startX = pianoConfiguration.bassLinesStartX,
                lineColor = pianoConfiguration.color
            )

            // top lines
            drawMusicLines(
                pianoConfiguration = pianoConfiguration,
                startX = pianoConfiguration.topLinesStartX,
                lineColor = pianoConfiguration.color
            )

            // edges
            drawLine(
                color = pianoConfiguration.color,
                start = Offset(
                    pianoConfiguration.startEdgeX,
                    pianoConfiguration.startEdgeY
                ),
                end = Offset(
                    pianoConfiguration.endEdgeX,
                    pianoConfiguration.endEdgeY
                ),
                strokeWidth = pianoConfiguration.strokeWidth
            )

            drawLine(
                color = pianoConfiguration.color,
                start = Offset(
                    pianoConfiguration.startEdgeX,
                    size.height
                ),
                end = Offset(
                    pianoConfiguration.endEdgeX,
                    size.height
                ),
                strokeWidth = pianoConfiguration.strokeWidth
            )

            // clefs
            drawableToImageBitmap(
                drawable = drawableTrebleClef,
                height = pianoConfiguration.trebleClefHeight,
                width = pianoConfiguration.trebleClefWidth,
                rotationDegrees = 90f
            )?.let { bitmap ->
                drawImage(
                    image = bitmap,
                    topLeft = pianoConfiguration.trebleClefOffset
                )
            }

            drawableToImageBitmap(
                drawable = drawableBassClef,
                height = pianoConfiguration.bassClefHeight,
                width = pianoConfiguration.bassClefWidth,
                rotationDegrees = 90f
            )?.let { bitmap ->
                drawImage(
                    image = bitmap,
                    topLeft = pianoConfiguration.bassClefOffset
                )
            }

            // notes
            drawLiveNotes(
                pianoConfiguration = pianoConfiguration,
                liveNotes = liveNotes,
            )
        }
    }
}

fun DrawScope.drawMusicLines(
    pianoConfiguration: PianoConfiguration,
    startX: Float,
    lineColor: Color
) {
    val startY = pianoConfiguration.topPadding
    val endY = size.height
    for (i in 0 until pianoConfiguration.lineCount) {
        val xOffset = startX + i * pianoConfiguration.lineSpacing
        drawLine(
            color = lineColor,
            start = Offset(xOffset, startY),
            end = Offset(xOffset, endY),
            strokeWidth = pianoConfiguration.strokeWidth
        )
    }
}

fun DrawScope.drawLiveNotes(
    pianoConfiguration: PianoConfiguration,
    liveNotes: List<Pair<List<Note>, Int>>
) {
    liveNotes.forEach { (notes, timeMoment) ->

        notes.forEach { note ->
            val cordX =
                note.value * pianoConfiguration.halfLineSpacing +
                        if (note.value > pianoConfiguration.firstBassNote) {
                            pianoConfiguration.notePaddingTop
                        } else {
                            pianoConfiguration.notePaddingBottom
                        }
            val cordY = timeMoment * (size.height / pianoConfiguration.noteCountWithPadding)

            val isNeedAddLine = pianoConfiguration.needLineNotesMap.contains(note.value)
            if (isNeedAddLine) {
                val lineCordX = if (note.value % 2 == 1) {
                    cordX + pianoConfiguration.halfLineSpacing
                } else {
                    cordX + pianoConfiguration.lineSpacing
                }
                val lineCount = pianoConfiguration.needLineNotesMap[note.value] ?: 0
                val isBottomLines = pianoConfiguration.bottomLineNotesList.contains(note.value)
                for (i in 0 until lineCount) {
                    val lineFinalCordX = if (isBottomLines) {
                        lineCordX + (pianoConfiguration.lineSpacing * i)
                    } else {
                        lineCordX - (pianoConfiguration.lineSpacing * i)
                    }
                    drawLine(
                        start = Offset(
                            lineFinalCordX,
                            cordY + pianoConfiguration.topNoteLinePadding
                        ),
                        end = Offset(
                            lineFinalCordX,
                            cordY + pianoConfiguration.bottomNoteLinePadding
                        ),
                        strokeWidth = pianoConfiguration.strokeWidth,
                        color = pianoConfiguration.color,
                    )
                }
            }

            drawOval(
                color = pianoConfiguration.color,
                size = Size(pianoConfiguration.lineSpacing, pianoConfiguration.noteWidth),
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
        listOf(Note(value = 1, isWhiteKey = true)) to 1,
        listOf(
            Note(value = 10, isWhiteKey = true),

            ) to 2,
        listOf(
            Note(value = 11, isWhiteKey = true),

            ) to 3,
        listOf(
            Note(value = 63, isWhiteKey = true),
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
        pianoConfiguration = PianoConfiguration(),
        liveNotes = liveNotes
    )
}