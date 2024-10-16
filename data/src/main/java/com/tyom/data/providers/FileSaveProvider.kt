package com.tyom.data.providers

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.tyom.core_ui.R
import com.tyom.core_ui.constants.NoteConstants.C3
import com.tyom.core_ui.constants.NoteConstants.C4
import com.tyom.core_ui.constants.NoteConstants.C5
import com.tyom.core_ui.constants.NoteConstants.DEFAULT
import com.tyom.core_utils.extensions.ifFalse
import com.tyom.core_utils.utils.sendExceptionToFirebase
import com.tyom.domain.models.MusicalComposition
import com.tyom.domain.models.Note
import com.tyom.domain.models.NoteListConfiguration
import com.tyom.domain.models.NotePairs
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class FileSaveProvider(
    @ApplicationContext val context: Context,
) {
    private val STANDARD_DPI = 300
    private val STANDARD_HEIGHT = 8.27
    private val STANDARD_WIDTH = 11.69
    private val DIEZ_SYMBOL = "♯"
    private val BEMOL_SYMBOL = "♭"

    fun saveCanvasAsA4Image(
        context: Context,
        noteListConfiguration: NoteListConfiguration,
        musicalComposition: MusicalComposition,
    ): String {
        val heightPx = (STANDARD_HEIGHT * STANDARD_DPI).toInt()
        val widthPx = (STANDARD_WIDTH * STANDARD_DPI).toInt()

        val bitmap = Bitmap.createBitmap(widthPx, heightPx, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)

        var strokeCount = if (musicalComposition.notesPairs.size > noteListConfiguration.noteCount) {
            ((musicalComposition.notesPairs.size).toFloat() / noteListConfiguration.noteCount.toFloat()).roundToInt()
        } else {
            1
        }
        val pages = (strokeCount / 5f).roundToInt()

        for (i in 0 until strokeCount) {
            val offsetX = noteListConfiguration.widthFromStrokes * (i + 1)
            val (start, end) = getSliceIndexes(
                i,
                noteListConfiguration.noteCount,
                musicalComposition.notesPairs.size
            )

            drawMusicLinesOnCanvas(
                canvas,
                startX = canvas.width - offsetX + noteListConfiguration.topLinesStartX,
                noteListConfiguration
            )
            drawMusicLinesOnCanvas(
                canvas,
                startX = canvas.width - offsetX + noteListConfiguration.bassLinesStartX,
                noteListConfiguration
            )
            drawClefsOnCanvas(canvas, noteListConfiguration, canvas.width - offsetX)
            drawEdgesOnCanvas(canvas, startX = canvas.width - offsetX, noteListConfiguration)
            if (start < musicalComposition.notesPairs.size) {
                val safeEnd = minOf(end, musicalComposition.notesPairs.size)
                val sublist = musicalComposition.notesPairs.subList(start, safeEnd)

                drawLiveNotesOnCanvas(
                    startX = canvas.width - offsetX,
                    canvas = canvas,
                    pianoConfiguration = noteListConfiguration,
                    liveNotes = sublist
                )
            }
        }
        drawTitleOnCanvas(noteListConfiguration, canvas)

        val values = ContentValues()
        val fileName = changeFileName(context, "NoteStudio_${musicalComposition.title}") + ".jpeg"
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
        }
        val resolver = context.contentResolver
        var uri: Uri? = null
        try {
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            uri = resolver.insert(contentUri, values)
            if (uri == null) {
                throw IOException("Failed to create new MediaStore record.")
            }
            resolver.openOutputStream(uri).use { stream ->
                if (stream == null) {
                    throw IOException("Failed to open output stream.")
                }
                if (!bitmap.compress(Bitmap.CompressFormat.PNG, 95, stream)) {
                    throw IOException("Failed to save bitmap.")
                }
            }
        } catch (e: IOException) {
            if (uri != null) {
                resolver.delete(uri, null, null)
            }
            sendExceptionToFirebase("saveCanvasAsA4Image", e)
            throw e
        }

        return fileName
    }

    private fun drawMusicLinesOnCanvas(
        canvas: Canvas,
        startX: Float,
        pianoConfiguration: NoteListConfiguration,
    ) {
        val paint = android.graphics.Paint().apply {
            color = pianoConfiguration.color
            strokeWidth = pianoConfiguration.strokeWidth
        }
        val startY = pianoConfiguration.topPadding + pianoConfiguration.a4Paddings
        val endY = canvas.height.toFloat() - pianoConfiguration.a4Paddings

        for (i in 0 until pianoConfiguration.lineCount) {
            val xOffset = startX + i * pianoConfiguration.lineSpacing
            canvas.drawLine(
                xOffset,
                startY,
                xOffset,
                endY,
                paint
            )
        }
    }

    private fun drawEdgesOnCanvas(
        canvas: Canvas,
        startX: Float,
        pianoConfiguration: NoteListConfiguration,
    ) {
        val paint = android.graphics.Paint().apply {
            color = pianoConfiguration.color
            strokeWidth = pianoConfiguration.strokeWidth
        }

        canvas.drawLine(
            pianoConfiguration.startEdgeX + startX,
            pianoConfiguration.startEdgeY,
            pianoConfiguration.endEdgeX + startX,
            pianoConfiguration.endEdgeY,
            paint
        )

        canvas.drawLine(
            pianoConfiguration.startEdgeX + startX,
            canvas.height - pianoConfiguration.a4Paddings,
            pianoConfiguration.endEdgeX + startX,
            canvas.height - pianoConfiguration.a4Paddings,
            paint
        )
    }

    private fun drawLiveNotesOnCanvas(
        startX: Float,
        canvas: Canvas,
        pianoConfiguration: NoteListConfiguration,
        liveNotes: List<NotePairs>,
    ) {
        val paint = android.graphics.Paint().apply {
            color = pianoConfiguration.color
        }
        val strokePaint = android.graphics.Paint().apply {
            color = pianoConfiguration.color
            strokeWidth = pianoConfiguration.strokeWidth
        }

        liveNotes.forEach { (notes, order) ->
            val timeMoment = (order % pianoConfiguration.noteCount) + 1

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
                    val isVerticalLine =
                        ((average ?: DEFAULT) <= C5 && (average ?: DEFAULT) >= C4) ||
                                (average ?: DEFAULT) <= C3

                    val cordX =
                        note.value * pianoConfiguration.halfLineSpacing + startX +
                                if (note.value > pianoConfiguration.firstBassNote) {
                                    pianoConfiguration.notePaddingTop
                                } else {
                                    pianoConfiguration.notePaddingBottom
                                }
                    var cordY =
                        timeMoment * (canvas.height / pianoConfiguration.noteCountWithPadding) + pianoConfiguration.a4Paddings

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
                        val isBottomLines =
                            pianoConfiguration.bottomLineNotesList.contains(note.value)
                        for (i in 0 until lineCount) {
                            val lineFinalCordX = if (isBottomLines) {
                                lineCordX + (pianoConfiguration.lineSpacing * i)
                            } else {
                                lineCordX - (pianoConfiguration.lineSpacing * i)
                            }
                            canvas.drawLine(
                                lineFinalCordX,
                                cordY + pianoConfiguration.topNoteLinePadding,
                                lineFinalCordX,
                                cordY + pianoConfiguration.bottomNoteLinePadding,
                                strokePaint
                            )
                        }

                    }

                    canvas.save()
                    canvas.rotate(
                        -20f,
                        cordX + pianoConfiguration.lineSpacing / 2,
                        cordY + pianoConfiguration.noteWidth / 2
                    )

                    val ovalRect = RectF(
                        cordX,
                        cordY,
                        cordX + pianoConfiguration.lineSpacing,
                        cordY + pianoConfiguration.noteWidth
                    )
                    canvas.drawOval(ovalRect, paint)
                    canvas.restore()

                    if (isVerticalLine) {
                        canvas.drawLine(
                            cordX + pianoConfiguration.halfLineSpacing,
                            cordY + if (isNeedPaddingForVerticalLine) {
                                pianoConfiguration.strokeWidth * 2
                            } else {
                                pianoConfiguration.noteWidth - pianoConfiguration.halfStrokeWidth
                            },
                            cordX + pianoConfiguration.verticalLineHeight,
                            cordY + if (isNeedPaddingForVerticalLine) {
                                pianoConfiguration.strokeWidth * 2
                            } else {
                                pianoConfiguration.noteWidth - pianoConfiguration.halfStrokeWidth
                            },
                            strokePaint
                        )
                    } else {
                        canvas.drawLine(
                            cordX + pianoConfiguration.halfLineSpacing,
                            cordY + if (isNeedPaddingForVerticalLine) {
                                pianoConfiguration.strokeWidth * 2
                            } else {
                                pianoConfiguration.noteWidth - pianoConfiguration.halfStrokeWidth
                            },
                            cordX - pianoConfiguration.verticalLineHeight + pianoConfiguration.lineSpacing,
                            cordY + if (isNeedPaddingForVerticalLine) {
                                pianoConfiguration.strokeWidth * 2
                            } else {
                                pianoConfiguration.noteWidth - pianoConfiguration.halfStrokeWidth
                            },
                            strokePaint
                        )
                    }

                    note.isWhiteKey.ifFalse {
                        val (symbol, angle) = if (pianoConfiguration.isDiez) {
                            DIEZ_SYMBOL to 90f
                        } else {
                            BEMOL_SYMBOL to 78f
                        }
                        canvas.save()

                        canvas.rotate(
                            angle,
                            cordX,
                            cordY
                        )

                        val paint = android.graphics.Paint().apply {
                            color = Color.BLACK
                            textSize = pianoConfiguration.symbolSize
                            isAntiAlias = true
                            typeface = ResourcesCompat.getFont(
                                context,
                                R.font.nunito_sanst_condensed_medium_italic
                            )
                        }

                        val xPosition = cordX - pianoConfiguration.symbolPaddingX -
                                if (isNeedPaddingForVerticalLine) pianoConfiguration.symbolPaddingX else 0f
                        val yPosition = cordY - pianoConfiguration.symbolPaddingY

                        canvas.drawText(symbol, xPosition, yPosition, paint)

                        canvas.restore()
                    }

                }
            }

        }
    }

    private fun drawTitleOnCanvas(
        pianoConfiguration: NoteListConfiguration,
        canvas: Canvas
    ) {
        val typefaceChoice = ResourcesCompat.getFont(context, pianoConfiguration.font.fontRes)
        val paint = android.graphics.Paint().apply {
            color = pianoConfiguration.color
            typeface = typefaceChoice
            textSize = 90f
            isAntiAlias = true
            textAlign = android.graphics.Paint.Align.CENTER
        }
        canvas.save()

        val xPosition = canvas.width - pianoConfiguration.paddingForTitle
        val yPosition = canvas.height / 2f

        canvas.rotate(90f, xPosition, yPosition)

        canvas.drawText(
            pianoConfiguration.title,
            xPosition,
            yPosition,
            paint
        )

        canvas.restore()
    }

    private fun drawClefsOnCanvas(
        canvas: Canvas,
        pianoConfiguration: NoteListConfiguration,
        startX: Float,
    ) {
        val paint = android.graphics.Paint().apply {
            color = pianoConfiguration.color
        }
        val drawableTrebleClef = context.getDrawable(R.drawable.treble_clef)
        val drawableBassClef = context.getDrawable(R.drawable.bass_clef)

        val matrix = android.graphics.Matrix()

        drawableTrebleClef?.toBitmap(
            pianoConfiguration.trebleClefWidth,
            pianoConfiguration.trebleClefHeight,
        )?.let { bitmap ->
            matrix.reset()
            matrix.postRotate(90f, bitmap.width / 2f, bitmap.height / 2f)
            matrix.postTranslate(
                pianoConfiguration.trebleClefX + startX,
                pianoConfiguration.trebleClefY
            )
            canvas.drawBitmap(bitmap, matrix, paint)
        }

        drawableBassClef?.toBitmap(
            pianoConfiguration.bassClefWidth,
            pianoConfiguration.bassClefHeight,
        )?.let { bitmap ->
            matrix.reset()
            matrix.postRotate(90f, bitmap.width / 2f, bitmap.height / 2f)
            matrix.postTranslate(
                pianoConfiguration.bassClefX + startX,
                pianoConfiguration.bassClefY
            )
            canvas.drawBitmap(bitmap, matrix, paint)
        }
    }

    internal fun fileExists(context: Context, fileName: String): Boolean {
        val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)
        val selection = "${MediaStore.MediaColumns.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(fileName)

        val queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        context.contentResolver.query(
            queryUri,
            projection,
            selection,
            selectionArgs,
            null
        ).use { cursor ->
            return cursor?.count ?: 0 > 0
        }
    }

    internal fun changeFileName(context: Context, name: String, number: Int = 1): String {
        if (fileExists(context, name)){
            return changeFileName(context, "$name ($number)", number + 1)
        } else if (number == 1){
            return name
        } else {
            return name + "$name ($number)"
        }
    }

    internal fun getSliceIndexes(i: Int, maxNoteCount: Int, listSize: Int): Pair<Int, Int> {
        if (listSize == 0) {
            return 0 to 0
        }
        if (listSize < maxNoteCount) {
            return 0 to (listSize - 1)
        }
        val start = i * maxNoteCount
        val end = (i + 1) * maxNoteCount + 1
        return start to end
    }

    internal fun calculateYPadding(
        notes: List<Note>,
        cordY: Float = 0f,
        note: Note,
        depth: Int = 1,
        pianoConfiguration: NoteListConfiguration
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

}