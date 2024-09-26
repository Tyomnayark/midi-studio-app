package com.tyom.data.repository

import com.tyom.data.models.MusicalCompositionRealm
import com.tyom.data.models.NotePairRealm
import com.tyom.data.models.NoteRealm
import com.tyom.domain.models.MusicalComposition
import com.tyom.domain.models.Note
import com.tyom.domain.models.NotePairs
import com.tyom.domain.repository.DataBaseRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import javax.inject.Inject

class DataBaseRepositoryImpl @Inject constructor(
    private val realm: Realm
) : DataBaseRepository {

    private val TITLE_FIELD = "title == $0"

    override suspend fun saveMusicComposition(musicalComposition: MusicalComposition): Boolean {
        return try {
            val musicalCompositionRealm = MusicalCompositionRealm().apply {
                this.title = musicalComposition.title

                this.notePairRealms = musicalComposition.notesPairs.map { notesPairs ->
                    NotePairRealm().apply {
                        this.notes = notesPairs.notes.map { note ->
                            NoteRealm().apply {
                                this.value = note.value
                                this.isWhiteKey = note.isWhiteKey
                            }
                        }.let { realmListOf(*it.toTypedArray()) }

                        this.orderNumber = notesPairs.orderNumber
                    }
                }.let { realmListOf(*it.toTypedArray()) }
            }

            realm.writeBlocking {
                copyToRealm(musicalCompositionRealm)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun getAllMusicCompositions(): List<MusicalComposition> {
        return try {
            val compositions = realm.query<MusicalCompositionRealm>().find()

            compositions.map { composition ->
                val notePairs = composition.notePairRealms.map { notePair ->
                    val notesList = notePair.notes.map { noteRealm ->
                        Note(
                            value = noteRealm.value,
                            isWhiteKey = noteRealm.isWhiteKey
                        )
                    }
                    NotePairs(notesList, notePair.orderNumber)
                }

                MusicalComposition(composition.title, notePairs)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun deleteMusicComposition(title: String): Boolean {
        return try {
            realm.write {
                val compositionToDelete = this.query<MusicalCompositionRealm>(TITLE_FIELD, title)
                    .first()
                    .find()

                if (compositionToDelete != null) {
                    delete(compositionToDelete)
                    true
                } else {
                    false
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}