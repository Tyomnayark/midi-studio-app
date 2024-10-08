package com.tyom.data.repository

import android.content.Context
import com.tyom.data.providers.FileSaveProvider
import com.tyom.domain.models.MusicalComposition
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
        musicalComposition: MusicalComposition,
    ): Boolean {
        return fileSaveProvider.saveCanvasAsA4Image(
            context = context,
            noteListConfiguration = noteListConfiguration,
            musicalComposition = musicalComposition
        )
    }
}