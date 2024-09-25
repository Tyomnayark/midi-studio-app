package com.tyom.domain.repository

import com.tyom.core_ui.models.Note

interface DataBaseRepository {

    suspend fun saveMusicComposition(title: String, notes: List<Pair<List<Note>, Int>>): Boolean

}