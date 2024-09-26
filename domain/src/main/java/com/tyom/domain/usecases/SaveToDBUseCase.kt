package com.tyom.domain.usecases

import com.tyom.domain.models.MusicalComposition
import com.tyom.domain.repository.DataBaseRepository
import javax.inject.Inject

class SaveToDBUseCase @Inject constructor(
    private val dataBaseRepository: DataBaseRepository
) {
    suspend fun execute(musicalComposition: MusicalComposition): Boolean {
        return dataBaseRepository.saveMusicComposition(musicalComposition)
    }
}