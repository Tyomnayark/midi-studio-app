package com.tyom.domain.usecases

import com.tyom.core_ui.models.Note
import com.tyom.domain.repository.DataBaseRepository
import javax.inject.Inject

class SaveToDBUseCase @Inject constructor(
    private val dataBaseRepository: DataBaseRepository
) {
    suspend fun execute(title: String, notes: List<Pair<List<Note>, Int>>): Boolean {
        return dataBaseRepository.saveMusicComposition(title, notes)
    }
}