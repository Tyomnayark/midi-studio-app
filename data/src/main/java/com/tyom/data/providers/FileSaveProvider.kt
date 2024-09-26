package com.tyom.data.providers

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.tyom.core_ui.R
import com.tyom.domain.models.Note
import com.tyom.domain.models.NoteListConfiguration
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Singleton

@Singleton
class FileSaveProvider(
    @ApplicationContext val context: Context,
) {
    private val STANDARD_DPI = 300
    private val STANDARD_HEIGHT = 8.27
    private val STANDARD_WIDTH = 11.69

    fun saveCanvasAsA4Image(
        context: Context,
        noteListConfiguration: NoteListConfiguration,
        liveNotes: List<Pair<List<Note>, Int>>,
    ): Boolean {
        val heightPx = (STANDARD_HEIGHT * STANDARD_DPI).toInt()
        val widthPx = (STANDARD_WIDTH * STANDARD_DPI).toInt()

        val bitmap = Bitmap.createBitmap(widthPx, heightPx, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)

        val strokeCount = 5

        for (i in 0..strokeCount) {
            val offsetX = noteListConfiguration.widthFromStrokes * i
            drawMusicLinesOnCanvas(
                canvas,
                startX = canvas.width + noteListConfiguration.bassLinesStartX - offsetX,
                noteListConfiguration
            )
            drawMusicLinesOnCanvas(
                canvas,
                startX = canvas.width + noteListConfiguration.topLinesStartX - offsetX,
                noteListConfiguration
            )
            drawClefsOnCanvas(canvas, noteListConfiguration, canvas.width - offsetX)
            drawEdgesOnCanvas(canvas, startX = canvas.width - offsetX, noteListConfiguration)
            drawLiveNotesOnCanvas(
                startX = canvas.width - offsetX,
                canvas = canvas,
                pianoConfiguration = noteListConfiguration,
                liveNotes = liveNotes
            )
        }

        val values = ContentValues()
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "IMG_" + System.currentTimeMillis())
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        if (SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
        }
        val resolver = context.contentResolver
        var uri: Uri? = null
        try {
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            uri = resolver.insert(contentUri, values)
            if (uri == null) {
                //isSuccess = false;
                throw IOException("Failed to create new MediaStore record.")
            }
            resolver.openOutputStream(uri).use { stream ->
                if (stream == null) {
                    //isSuccess = false;
                    throw IOException("Failed to open output stream.")
                }
                if (!bitmap.compress(Bitmap.CompressFormat.PNG, 95, stream)) {
                    //isSuccess = false;
                    throw IOException("Failed to save bitmap.")
                }
            }
            //isSuccess = true;
        } catch (e: IOException) {
            if (uri != null) {
                resolver.delete(uri, null, null)
            }
            throw e
        }

        return true
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
        liveNotes: List<Pair<List<Note>, Int>>,
    ) {
        val paint = android.graphics.Paint().apply {
            color = pianoConfiguration.color
        }
        val strokePaint = android.graphics.Paint().apply {
            color = pianoConfiguration.color
            strokeWidth = pianoConfiguration.strokeWidth
        }

        liveNotes.forEach { (notes, timeMoment) ->
            notes.forEach { note ->
                val cordX =
                    note.value * pianoConfiguration.halfLineSpacing +
                            if (note.value > pianoConfiguration.firstBassNote) {
                                pianoConfiguration.notePaddingTop
                            } else {
                                pianoConfiguration.notePaddingBottom
                            } +
                            startX

                val cordY =
                    timeMoment * (canvas.height / pianoConfiguration.noteCountWithPadding) + pianoConfiguration.a4Paddings / 2
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
                        canvas.drawLine(
                            lineFinalCordX,
                            cordY + pianoConfiguration.topNoteLinePadding,
                            lineFinalCordX,
                            cordY + pianoConfiguration.bottomNoteLinePadding,
                            strokePaint
                        )
                    }
                }
                canvas.drawOval(
                    cordX,
                    cordY,
                    cordX + pianoConfiguration.lineSpacing,
                    cordY + pianoConfiguration.noteWidth,
                    paint
                )
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
            typeface = typeface
        }

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
}