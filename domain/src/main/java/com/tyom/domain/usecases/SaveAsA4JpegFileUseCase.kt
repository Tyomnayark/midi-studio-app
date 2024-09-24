package com.tyom.domain.usecases

import com.tyom.domain.models.NoteModel
import com.tyom.domain.models.PianoSettings
import com.tyom.domain.repository.FileSaveRepository
import javax.inject.Inject

class SaveAsA4JpegFileUseCase @Inject constructor(
    private val fileSaveRepository: FileSaveRepository
) {
    suspend fun execute(pianoSettings: PianoSettings, liveNotes: List<Pair<List<NoteModel>, Int>>){
        fileSaveRepository.saveAsA4Jpeg(pianoSettings, liveNotes)
    }
}