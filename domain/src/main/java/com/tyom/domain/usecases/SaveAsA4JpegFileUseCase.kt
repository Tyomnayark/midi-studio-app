package com.tyom.domain.usecases

import com.tyom.domain.models.MusicalComposition
import com.tyom.domain.models.NoteListConfiguration
import com.tyom.domain.repository.FileSaveRepository
import javax.inject.Inject

class SaveAsA4JpegFileUseCase @Inject constructor(
    private val fileSaveRepository: FileSaveRepository
) {
    suspend fun execute(noteListConfiguration: NoteListConfiguration, musicalComposition: MusicalComposition){
        fileSaveRepository.saveAsA4Jpeg(noteListConfiguration, musicalComposition)
    }
}