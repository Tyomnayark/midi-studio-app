package com.tyom.data
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tyom.data.providers.FileSaveProvider
import com.tyom.domain.models.MusicalComposition
import com.tyom.domain.models.NoteListConfiguration
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FileSaveProviderInstrumentedTest {

    private lateinit var context: Context
    private lateinit var fileSaveProvider: FileSaveProvider
    private lateinit var noteListConfiguration: NoteListConfiguration
    private lateinit var musicalComposition: MusicalComposition
    private lateinit var savedFileName: String
    private lateinit var result: String

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        fileSaveProvider = FileSaveProvider(context)
        savedFileName = "test_notes"
        musicalComposition = MusicalComposition(title = savedFileName)
        noteListConfiguration = NoteListConfiguration(title = "")
    }

    @Test
    fun testSaveCanvasAsA4Image() {
        val result = fileSaveProvider.saveCanvasAsA4Image(context, noteListConfiguration, musicalComposition)
        val fileExists = fileSaveProvider.fileExists(context, result)
        this.result = result

        assertTrue("File $result must exist in storage", fileExists)
    }

    @After
    fun tearDown() {
        deleteFileFromStorage(result)
    }

    private fun deleteFileFromStorage(fileName: String) {
        val uri = getFileUri(fileName)
        uri?.let {
            context.contentResolver.delete(it, null, null)
        }
    }

    private fun getFileUri(fileName: String): Uri? {
        val projection = arrayOf(MediaStore.MediaColumns._ID)
        val selection = "${MediaStore.MediaColumns.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(fileName)

        context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())
            }
        }
        return null
    }
}