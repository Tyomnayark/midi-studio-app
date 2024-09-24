package com.tyom.domain.repository

import com.tyom.domain.models.NoteModel
import com.tyom.domain.models.PianoSettings

interface FileSaveRepository {
    suspend fun saveAsA4Jpeg(pianoSettings: PianoSettings, liveNotes: List<Pair<List<NoteModel>, Int>>): Boolean
}