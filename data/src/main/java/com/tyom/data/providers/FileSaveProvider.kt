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
import com.tyom.domain.models.NoteModel
import com.tyom.domain.models.PianoSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Singleton

@Singleton
class FileSaveProvider (
    @ApplicationContext context: Context
) {
    private val STANDARD_DPI = 300

    fun saveCanvasAsA4Image(
        context: Context,
        pianoSettings: PianoSettings,
        liveNotes: List<Pair<List<NoteModel>, Int>>,
    ): Boolean {
        val widthPx = (8.27 * STANDARD_DPI).toInt()  // 8.27 дюйма для ширины A4
        val heightPx = (11.69 * STANDARD_DPI).toInt()  // 11.69 дюйма для высоты A4

        val bitmap = Bitmap.createBitmap(widthPx, heightPx, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)

        canvas.drawColor(Color.WHITE)

        // 5. Отрисовываем на Canvas (аналогично тому, как вы делаете это в Compose)
        drawMusicLinesOnCanvas(canvas, pianoSettings)
        drawLiveNotesOnCanvas(canvas, pianoSettings, liveNotes)

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
        pianoConfiguration: PianoSettings
    ) {
        val paint = android.graphics.Paint().apply {
            color = pianoConfiguration.color
            strokeWidth = pianoConfiguration.strokeWidth
        }

        val startY = pianoConfiguration.topPadding
        val endY = canvas.height.toFloat()
        for (i in 0 until pianoConfiguration.lineCount) {
            val xOffset = pianoConfiguration.bassLinesStartX + i * pianoConfiguration.lineSpacing
            canvas.drawLine(
                xOffset,
                startY,
                xOffset,
                endY,
                paint
            )
        }
    }

    private fun drawLiveNotesOnCanvas(
        canvas: Canvas,
        pianoConfiguration: PianoSettings,
        liveNotes: List<Pair<List<NoteModel>, Int>>
    ) {
        val paint = android.graphics.Paint().apply {
            color = pianoConfiguration.color
        }

        liveNotes.forEach { (notes, timeMoment) ->
            notes.forEach { note ->
                val cordX =
                    note.value * pianoConfiguration.halfLineSpacing +
                            if (note.value > pianoConfiguration.firstBassNote) {
                                pianoConfiguration.notePaddingTop
                            } else {
                                pianoConfiguration.notePaddingBottom
                            }
                val cordY = timeMoment * (canvas.height / pianoConfiguration.noteCountWithPadding)

                canvas.drawOval(
                    cordX,
                    cordY.toFloat(),
                    cordX + pianoConfiguration.lineSpacing,
                    cordY + pianoConfiguration.noteWidth,
                    paint
                )
            }
        }
    }
}