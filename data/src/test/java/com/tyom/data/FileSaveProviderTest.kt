package com.tyom.data

import com.tyom.data.providers.FileSaveProvider
import com.tyom.domain.models.Note
import com.tyom.domain.models.NoteListConfiguration
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class FileSaveProviderTest {

    private lateinit var fileSaveProvider: FileSaveProvider
    private lateinit var noteListConfiguration: NoteListConfiguration

    @Before
    fun setUp() {
        fileSaveProvider = FileSaveProvider(mockk())
        noteListConfiguration = NoteListConfiguration(title = "")
    }

    @Test
    fun calculateYPaddingTest(){
        val notes = listOf(
            Note(20, true, false, 0),
            Note(21, false, false, 0),
            Note(22, false, false, 0),
            Note(23, false, false, 0),
            Note(24, false, false, 0),
            Note(26, false, false, 0),
            Note(27, false, false, 0)
        )
        val expectedZeroPadding = 0f
        val expectedPadding = noteListConfiguration.noteWidth * 0.8f

        var padding = fileSaveProvider.calculateYPadding(notes, note = notes[0], pianoConfiguration = noteListConfiguration)
        println("FileSaveProviderTest: padding for note 0: $padding")
        assertEquals(expectedZeroPadding, padding)

        padding = fileSaveProvider.calculateYPadding(notes, note = notes[1], pianoConfiguration = noteListConfiguration)
        println("FileSaveProviderTest: padding for note 1: $padding")
        assertEquals(expectedPadding, padding)

        padding = fileSaveProvider.calculateYPadding(notes, note = notes[2], pianoConfiguration = noteListConfiguration)
        println("FileSaveProviderTest: padding for note 2: $padding")
        assertEquals(expectedZeroPadding, padding)

        padding = fileSaveProvider.calculateYPadding(notes, note = notes[3], pianoConfiguration = noteListConfiguration)
        println("FileSaveProviderTest: padding for note 4: $padding")
        assertEquals(expectedPadding, padding)

        padding = fileSaveProvider.calculateYPadding(notes, note = notes[4], pianoConfiguration = noteListConfiguration)
        println("FileSaveProviderTest: padding for note 4: $padding")
        assertEquals(expectedZeroPadding, padding)

        padding = fileSaveProvider.calculateYPadding(notes, note = notes[5], pianoConfiguration = noteListConfiguration)
        println("FileSaveProviderTest: padding for note 5: $padding")
        assertEquals(expectedZeroPadding, padding)

        padding = fileSaveProvider.calculateYPadding(notes, note = notes[6], pianoConfiguration = noteListConfiguration)
        println("FileSaveProviderTest: padding for note 5: $padding")
        assertEquals(expectedPadding, padding)
    }
}