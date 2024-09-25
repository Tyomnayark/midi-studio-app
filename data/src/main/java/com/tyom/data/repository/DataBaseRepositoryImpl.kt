package com.tyom.data.repository

import com.tyom.core_ui.models.Note
import com.tyom.data.models.MusicalComposition
import com.tyom.domain.repository.DataBaseRepository
import io.realm.kotlin.Realm
import javax.inject.Inject

class DataBaseRepositoryImpl @Inject constructor(
    private val realm: Realm
) : DataBaseRepository {

    override suspend fun saveMusicComposition(
        title: String,
        notes: List<Pair<List<Note>, Int>>
    ): Boolean {
        try {
            val musicalComposition = MusicalComposition().apply {
                this.title = title
//                this.notes = notes
            }
            realm.writeBlocking {
                copyToRealm(musicalComposition)
            }
        } catch (E: Exception) {
            return false
        }
        return true
    }

}