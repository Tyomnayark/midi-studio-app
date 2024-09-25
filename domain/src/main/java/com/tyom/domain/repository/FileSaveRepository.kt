package com.tyom.domain.repository

import com.tyom.core_ui.models.Note
import com.tyom.domain.models.NoteListConfiguration

interface FileSaveRepository {
    suspend fun saveAsA4Jpeg(noteListConfiguration: NoteListConfiguration, liveNotes: List<Pair<List<Note>, Int>>): Boolean
}