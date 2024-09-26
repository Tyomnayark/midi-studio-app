package com.tyom.domain.usecases

import com.tyom.domain.repository.DataBaseRepository
import javax.inject.Inject

class DeleteMusicCompositionUseCase @Inject constructor(
    private val dataBaseRepository: DataBaseRepository
) {
    suspend fun execute(title: String): Boolean {
        return dataBaseRepository.deleteMusicComposition(title)
    }
}