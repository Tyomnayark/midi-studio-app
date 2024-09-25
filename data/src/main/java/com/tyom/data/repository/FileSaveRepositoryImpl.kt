package com.tyom.data.repository

import android.content.Context
import com.tyom.core_ui.models.Note
import com.tyom.data.providers.FileSaveProvider
import com.tyom.domain.models.NoteListConfiguration
import com.tyom.domain.repository.FileSaveRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FileSaveRepositoryImpl @Inject constructor(
    @ApplicationContext private var context: Context,
    private var fileSaveProvider: FileSaveProvider,
) : FileSaveRepository {
    override suspend fun saveAsA4Jpeg(
        noteListConfiguration: NoteListConfiguration,
        liveNotes: List<Pair<List<Note>, Int>>,
    ): Boolean {
        return fileSaveProvider.saveCanvasAsA4Image(
            context = context,
            noteListConfiguration = noteListConfiguration,
            liveNotes = liveNotes
        )
    }
}