package com.tyom.domain.usecases

import com.tyom.domain.models.MusicalComposition
import com.tyom.domain.repository.DataBaseRepository
import javax.inject.Inject

class GetAllMusicCompositionsUseCase @Inject constructor(
    private val dataBaseRepository: DataBaseRepository
) {
    suspend fun execute(): List<MusicalComposition>  {
        return dataBaseRepository.getAllMusicCompositions()
    }
}