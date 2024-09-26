package com.tyom.domain.repository

import com.tyom.domain.models.MusicalComposition

interface DataBaseRepository {

    suspend fun saveMusicComposition(musicalComposition: MusicalComposition): Boolean

    suspend fun getAllMusicCompositions(): List<MusicalComposition>

    suspend fun deleteMusicComposition(title: String): Boolean

}