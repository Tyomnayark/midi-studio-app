package com.example.feature_record.ui

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.rotate
import androidx.compose.ui.graphics.withSave
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.core.content.res.ResourcesCompat
import com.tyom.core_ui.constants.NoteConstants.C3
import com.tyom.core_ui.constants.NoteConstants.C4
import com.tyom.core_ui.constants.NoteConstants.C5
import com.tyom.core_ui.constants.NoteConstants.DEFAULT
import com.tyom.core_ui.extensions.FigmaLargePreview
import com.tyom.core_ui.models.PianoConfiguration
import com.tyom.core_ui.utils.drawableToImageBitmap
import com.tyom.core_utils.extensions.ifFalse
import com.tyom.domain.models.Note
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@SuppressLint("UseCompatLoadingForDrawables")
@Composable
fun LiveNoteString(
    modifier: Modifier = Modifier,
    pianoConfiguration: PianoConfiguration,
    liveNotes: Map<Int, List<Note>>,
    mapSize: Int,
    width: Dp = dimensionResource(id = com.tyom.core_ui.R.dimen._200dp)
) {
    val context = LocalContext.current

    val drawableTrebleClef = context.getDrawable(com.tyom.core_ui.R.drawable.treble_clef)
    val drawableBassClef = context.getDrawable(com.tyom.core_ui.R.drawable.bass_clef)

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
                mapSize = mapSize,
                context = context
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
    liveNotes: Map<Int, List<Note>>,
    mapSize: Int,
    context: Context
) {
    for (t in (mapSize - pianoConfiguration.noteCount) until mapSize) {
        val notes = liveNotes[t]
        val timeMoment = (mapSize - t - pianoConfiguration.noteCount).absoluteValue + 1

        notes?.forEach { note ->
            note.isRemoveCommand.ifFalse {
                val average =
                    notes?.filter { anotherNote -> !anotherNote.isRemoveCommand }
                        ?.map { anotherNote -> anotherNote.value }
                        ?.filter { anotherNote ->
                            if (anotherNote > pianoConfiguration.firstBassNote) {
                                note.value > pianoConfiguration.firstBassNote
                            } else {
                                note.value <= pianoConfiguration.firstBassNote
                            }
                        }
                        ?.average()?.roundToInt()
                val isVerticalLine = ((average ?: DEFAULT) <= C5 && (average ?: DEFAULT) >= C4) ||
                        (average ?: DEFAULT) <= C3

                val cordX =
                    note.value * pianoConfiguration.halfLineSpacing +
                            if (note.value > pianoConfiguration.firstBassNote) {
                                pianoConfiguration.notePaddingTop
                            } else {
                                pianoConfiguration.notePaddingBottom
                            }
                var cordY = timeMoment * (size.height / pianoConfiguration.noteCountWithPadding)

                val paddingY = if (notes.size > 1) {
                    calculateYPadding(
                        notes = notes,
                        note = note,
                        pianoConfiguration = pianoConfiguration
                    )
                } else {
                    0f
                }
                cordY += paddingY
                if (paddingY != 0f) {
                    cordY -= pianoConfiguration.strokeWidth
                }
                val isNeedPaddingForVerticalLine = if (paddingY == 0f) false else true

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

                drawIntoCanvas { canvas ->
                    canvas.withSave {
                        canvas.rotate(
                            degrees = -20f,
                            pivotX = cordX + pianoConfiguration.lineSpacing / 2,
                            pivotY = cordY + pianoConfiguration.noteWidth / 2
                        )
                        drawOval(
                            color = pianoConfiguration.color,
                            size = Size(
                                pianoConfiguration.lineSpacing,
                                pianoConfiguration.noteWidth
                            ),
                            topLeft = Offset(x = cordX, y = cordY)
                        )
                    }
                }

                if (isVerticalLine) {
                    drawLine(
                        color = pianoConfiguration.color,
                        start = Offset(
                            cordX + pianoConfiguration.halfLineSpacing,
                            cordY + if (isNeedPaddingForVerticalLine) {
                                pianoConfiguration.strokeWidth * 2
                            } else {
                                pianoConfiguration.noteWidth - pianoConfiguration.halfStrokeWidth
                            }
                        ),
                        end = Offset(
                            cordX + pianoConfiguration.verticalLineHeight,
                            cordY + if (isNeedPaddingForVerticalLine) {
                                pianoConfiguration.strokeWidth * 2
                            } else {
                                pianoConfiguration.noteWidth - pianoConfiguration.halfStrokeWidth
                            }
                        ),
                        strokeWidth = pianoConfiguration.strokeWidth
                    )
                } else {
                    drawLine(
                        color = pianoConfiguration.color,
                        start = Offset(
                            cordX + pianoConfiguration.halfLineSpacing,
                            cordY + if (isNeedPaddingForVerticalLine) {
                                pianoConfiguration.strokeWidth * 2
                            } else {
                                pianoConfiguration.noteWidth - pianoConfiguration.halfStrokeWidth
                            }
                        ),
                        end = Offset(
                            cordX - pianoConfiguration.verticalLineHeight + pianoConfiguration.lineSpacing,
                            cordY + if (isNeedPaddingForVerticalLine) {
                                pianoConfiguration.strokeWidth * 2
                            } else {
                                pianoConfiguration.noteWidth - pianoConfiguration.halfStrokeWidth
                            }
                        ),
                        strokeWidth = pianoConfiguration.strokeWidth
                    )
                }

                note.isWhiteKey.ifFalse {
                    drawIntoCanvas { canvas ->
                        val (symbol, angle) = if (pianoConfiguration.isDiez) {
                            "♯" to 90f
                        } else {
                            "♭" to 78f
                        }
                        canvas.withSave {
                            canvas.rotate(
                                degrees = angle,
                                pivotX = cordX,
                                pivotY = cordY
                            )
                            val paint = android.graphics.Paint().apply {
                                color = android.graphics.Color.BLACK
                                textSize = pianoConfiguration.symbolSize
                                isAntiAlias = true
                                typeface = ResourcesCompat.getFont(
                                    context,
                                    com.tyom.core_ui.R.font.nunito_sanst_condensed_medium_italic
                                )
                            }

                            val xPosition =
                                cordX - pianoConfiguration.symbolPaddingX - if (isNeedPaddingForVerticalLine) pianoConfiguration.symbolPaddingX else 0f
                            val yPosition = cordY - pianoConfiguration.symbolPaddingY

                            canvas.nativeCanvas.drawText(symbol, xPosition, yPosition, paint)
                        }

                    }

                }
            }
        }

    }
}

private fun calculateYPadding(
    notes: List<Note>,
    cordY: Float = 0f,
    note: Note,
    depth: Int = 1,
    pianoConfiguration: PianoConfiguration
): Float {
    if (depth == 0 || notes.isEmpty()) {
        return cordY
    }

    val currentNote = notes.find { it.value == note.value - depth }
    return if (currentNote != null) {
        val newCordY = if (depth % 2 == 1) {
            cordY + pianoConfiguration.noteWidth * 0.8f
        } else {
            cordY - pianoConfiguration.noteWidth * 0.8f
        }
        calculateYPadding(notes, newCordY, note, depth + 1, pianoConfiguration)
    } else {
        cordY
    }
}

@FigmaLargePreview
@Composable
fun LiveNoteStringPreview() {
    val liveNotes = mapOf(
        1 to listOf(
            Note(C4, false)
        ),
        2 to listOf(
            Note(C5, true)
        ),
    )
    LiveNoteString(
        pianoConfiguration = PianoConfiguration(),
        liveNotes = liveNotes,
        mapSize = 15
    )
}