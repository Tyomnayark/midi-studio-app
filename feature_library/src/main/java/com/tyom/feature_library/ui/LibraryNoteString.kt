package com.tyom.feature_library.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
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
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.rotate
import androidx.compose.ui.graphics.withSave
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
import com.tyom.domain.models.NotePairs
import kotlin.math.min
import kotlin.math.roundToInt

@SuppressLint("UseCompatLoadingForDrawables")
@Composable
fun LibraryNoteString(
    modifier: Modifier = Modifier,
    height: Dp,
    pianoConfiguration: PianoConfiguration,
    notes: List<NotePairs>
) {
    val context = LocalContext.current
    val drawableTrebleClef = context.getDrawable(com.tyom.core_ui.R.drawable.treble_clef)
    val drawableBassClef = context.getDrawable(com.tyom.core_ui.R.drawable.bass_clef)

    Box(
        modifier = modifier
            .background(
                color = pianoConfiguration.backgroundColor
            )
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
                    topLeft = Offset(
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
                context = context
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
    liveNotes: List<NotePairs>,
    context: Context
) {
    Log.d("TAG", "drawLiveNotes: ${liveNotes}")
    val start = 0
    val end = min(liveNotes.size, pianoConfiguration.noteCount)
    val sublist = liveNotes.subList(start, end)

    sublist.forEach { (notes, t) ->
        notes.forEach { note ->
            val timeMoment = t % pianoConfiguration.noteCountWithPadding + 1
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

                val cordY =
                    size.height - pianoConfiguration.padding - note.value * pianoConfiguration.halfLineSpacing -
                            if (note.value > pianoConfiguration.firstBassNote) {
                                pianoConfiguration.notePaddingTop - pianoConfiguration.lineSpacing
                            } else {
                                pianoConfiguration.notePaddingBottom - pianoConfiguration.lineSpacing
                            }
                var cordX = timeMoment * (size.width / pianoConfiguration.noteCountWithPadding)
                val paddingX = if (notes.size > 1) {
                    calculateYPadding(
                        notes = notes,
                        note = note,
                        pianoConfiguration = pianoConfiguration
                    )
                } else {
                    0f
                }
                cordX += paddingX
                if (paddingX != 0f) {
                    cordX -= pianoConfiguration.strokeWidth
                }
                val isNeedPaddingForVerticalLine = if (paddingX == 0f) false else true
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
                                pianoConfiguration.noteWidth,
                                pianoConfiguration.lineSpacing
                            ),
                            topLeft = Offset(
                                x = cordX,
                                y = cordY
                            )
                        )
                    }
                }

                if (isVerticalLine) {
                    drawLine(
                        color = pianoConfiguration.color,
                        start = Offset(
                            cordX + if (isNeedPaddingForVerticalLine) {
                                pianoConfiguration.halfLineSpacing
                            } else {
                                pianoConfiguration.noteWidth
                            }  - pianoConfiguration.halfStrokeWidth,
                            cordY + pianoConfiguration.halfLineSpacing
                        ),
                        end = Offset(
                            cordX + if (isNeedPaddingForVerticalLine) {
                                pianoConfiguration.halfLineSpacing - pianoConfiguration.halfStrokeWidth
                            } else {
                                pianoConfiguration.noteWidth - pianoConfiguration.halfStrokeWidth
                            },
                            cordY - pianoConfiguration.verticalLineHeight
                        ),
                        strokeWidth = pianoConfiguration.strokeWidth
                    )
                } else {
                    drawLine(
                        color = pianoConfiguration.color,
                        start = Offset(
                            cordX +  if (isNeedPaddingForVerticalLine) {
                                pianoConfiguration.halfLineSpacing
                            } else {
                                pianoConfiguration.noteWidth
                            }  - pianoConfiguration.halfStrokeWidth,
                            cordY + pianoConfiguration.halfStrokeWidth
                        ),
                        end = Offset(
                            cordX + if (isNeedPaddingForVerticalLine) {
                                pianoConfiguration.halfLineSpacing
                            } else {
                                pianoConfiguration.noteWidth
                            }  - pianoConfiguration.halfStrokeWidth,
                            cordY + pianoConfiguration.verticalLineHeight
                        ),
                        strokeWidth = pianoConfiguration.strokeWidth
                    )
                }

                note.isWhiteKey.ifFalse {
                    drawIntoCanvas { canvas ->
                        val (symbol, angle) = if (pianoConfiguration.isDiez) {
                            "♯" to 1f
                        } else {
                            "♭" to -10f
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
                                cordX - pianoConfiguration.symbolPaddingX - if (isNeedPaddingForVerticalLine) pianoConfiguration.symbolPaddingY else 0f
                            val yPosition = cordY - pianoConfiguration.symbolPaddingY + pianoConfiguration.lineSpacing

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
    val pianoConfiguration = PianoConfiguration(
        lineSpacing = 10f,
    )

    val height = (pianoConfiguration.widthFromStrokes / LocalDensity.current.density).dp

    val notesPairs = listOf(
        NotePairs(
            notes = listOf(
                Note(
                    value = 23,
                    isWhiteKey = false
                ),
                Note(
                    value = 24,
                    isWhiteKey = false
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
        pianoConfiguration = pianoConfiguration,
        height = height,
        notes = notesPairs
    )
}