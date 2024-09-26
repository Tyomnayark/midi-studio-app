package com.tyom.feature_library.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.tyom.domain.models.NotePairs

@SuppressLint("UseCompatLoadingForDrawables")
@Composable
fun LibraryNoteString(
    modifier: Modifier = Modifier,
    pianoConfiguration: PianoConfiguration,
    notes: List<NotePairs>
) {
    val context = LocalContext.current

    val drawableTrebleClef = context.getDrawable(com.tyom.core_ui.R.drawable.treble_clef)
    val drawableBassClef = context.getDrawable(com.tyom.core_ui.R.drawable.bass_clef)

    val height = (pianoConfiguration.widthFromStrokes / LocalDensity.current.density).dp

    Box(
        modifier = modifier
            .background(
                color = pianoConfiguration.backgroundColor)
    ) {
        Canvas(
            modifier = Modifier
                .padding(vertical = pianoConfiguration.lineSpacingDp)
                .fillMaxWidth()
                .height(height)
        ) {
            // bass lines
            drawMusicLines(
                pianoConfiguration = pianoConfiguration,
                startY = pianoConfiguration.bassLinesStartX,
                lineColor = pianoConfiguration.color
            )

            // top lines
            drawMusicLines(
                pianoConfiguration = pianoConfiguration,
                startY = pianoConfiguration.topLinesStartX,
                lineColor = pianoConfiguration.color
            )

            // clefs
            drawableToImageBitmap(
                drawable = drawableTrebleClef,
                height = pianoConfiguration.trebleClefHeight,
                width = pianoConfiguration.trebleClefWidth
            )?.let { bitmap ->
                drawImage(
                    image = bitmap,
                    topLeft =  Offset(
                        -pianoConfiguration.lineSpacing * 2,
                        pianoConfiguration.bassLinesStartX - pianoConfiguration.lineSpacing * 4
                    )
                )
            }

            drawableToImageBitmap(
                drawable = drawableBassClef,
                height = pianoConfiguration.bassClefHeight,
                width = pianoConfiguration.bassClefWidth
            )?.let { bitmap ->
                drawImage(
                    image = bitmap,
                    topLeft = Offset(
                        0f,
                        pianoConfiguration.topLinesStartX - pianoConfiguration.lineSpacing * 3
                    )
                )
            }

            // notes
            drawLiveNotes(
                pianoConfiguration = pianoConfiguration,
                liveNotes = notes,
            )
        }
    }
}

fun DrawScope.drawMusicLines(
    pianoConfiguration: PianoConfiguration,
    startY: Float,
    lineColor: Color
) {
    val endX = size.width
    for (i in 0 until pianoConfiguration.lineCount) {
        val xOffset = i * pianoConfiguration.lineSpacing
        drawLine(
            color = lineColor,
            start = Offset(0f, xOffset + startY),
            end = Offset(endX, xOffset + startY),
            strokeWidth = pianoConfiguration.strokeWidth
        )
    }
}

fun DrawScope.drawLiveNotes(
    pianoConfiguration: PianoConfiguration,
    liveNotes: List<NotePairs>
) {
    liveNotes.forEach { (notes, timeMoment) ->

        notes.forEach { note ->
            val cordY = size.height - pianoConfiguration.padding - note.value * pianoConfiguration.halfLineSpacing -
                        if (note.value > pianoConfiguration.firstBassNote) {
                            pianoConfiguration.notePaddingTop - pianoConfiguration.lineSpacing
                        } else {
                            pianoConfiguration.notePaddingBottom - pianoConfiguration.lineSpacing
                        }
            val cordX = timeMoment * (size.width / pianoConfiguration.noteCountWithPadding)

            val isNeedAddLine = pianoConfiguration.needLineNotesMap.contains(note.value)
            if (isNeedAddLine) {
                val isBottomLines = pianoConfiguration.bottomLineNotesList.contains(note.value)

                val lineCordY = if (note.value % 2 == 1) {
                    cordY + pianoConfiguration.halfLineSpacing
                } else {
                    if (!isBottomLines) {
                        cordY + pianoConfiguration.lineSpacing
                    } else {
                        cordY
                    }
                }
                val lineCount = pianoConfiguration.needLineNotesMap[note.value] ?: 0
                for (i in 0 until lineCount) {
                    val lineFinalCordY = if (isBottomLines) {
                        lineCordY - (pianoConfiguration.lineSpacing * i)
                    } else {
                        lineCordY + (pianoConfiguration.lineSpacing * i)
                    }
                    drawLine(
                        start = Offset(
                            cordX + pianoConfiguration.bottomNoteLinePadding,
                            lineFinalCordY
                        ),
                        end = Offset(
                            cordX + pianoConfiguration.topNoteLinePadding,
                            lineFinalCordY
                        ),
                        strokeWidth = pianoConfiguration.strokeWidth,
                        color = pianoConfiguration.color,
                    )
                }
            }

            drawOval(
                color = pianoConfiguration.color,
                size = Size(pianoConfiguration.noteWidth, pianoConfiguration.lineSpacing),
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
    val notesPairs = listOf(
        NotePairs(
            notes = listOf(
                Note(
                    value = 23,
                    isWhiteKey = true
                )
            ),
            orderNumber = 1
        ),
        NotePairs(
            notes = listOf(
                Note(
                    value = 32,
                    isWhiteKey = true
                ),
                Note(
                    value = 15,
                    isWhiteKey = true
                )
            ),
            orderNumber = 2
        ),
        NotePairs(
            notes = listOf(
                Note(
                    value = 13,
                    isWhiteKey = true
                ),
                Note(
                    value = 25,
                    isWhiteKey = true
                )
            ),
            orderNumber = 3
        )
    )
    LibraryNoteString(
        pianoConfiguration = PianoConfiguration(
            lineSpacing = 10f
        ),
        notes = notesPairs
    )
}