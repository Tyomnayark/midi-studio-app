package com.tyom.domain.repository

import com.tyom.domain.models.MusicalComposition
import com.tyom.domain.models.NoteListConfiguration

interface FileSaveRepository {
    suspend fun saveAsA4Jpeg(
        noteListConfiguration: NoteListConfiguration,
        musicalComposition: MusicalComposition
    ): Boolean
}