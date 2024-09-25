package com.tyom.domain.usecases

import com.tyom.core_ui.models.Note
import com.tyom.domain.models.NoteListConfiguration
import com.tyom.domain.repository.FileSaveRepository
import javax.inject.Inject

class SaveAsA4JpegFileUseCase @Inject constructor(
    private val fileSaveRepository: FileSaveRepository
) {
    suspend fun execute(noteListConfiguration: NoteListConfiguration, liveNotes: List<Pair<List<Note>, Int>>){
        fileSaveRepository.saveAsA4Jpeg(noteListConfiguration, liveNotes)
    }
}