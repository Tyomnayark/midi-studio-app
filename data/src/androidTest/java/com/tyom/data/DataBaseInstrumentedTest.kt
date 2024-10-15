package com.tyom.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tyom.data.models.MusicalCompositionRealm
import com.tyom.data.models.NotePairRealm
import com.tyom.data.models.NoteRealm
import com.tyom.data.repository.DataBaseRepositoryImpl
import com.tyom.domain.models.MusicalComposition
import com.tyom.domain.models.Note
import com.tyom.domain.models.NotePairs
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class DataBaseRepositoryTest {

    private lateinit var realm: Realm
    private lateinit var dataBaseRepository: DataBaseRepositoryImpl

    @Before
    fun setup() {
        val config = RealmConfiguration
            .Builder(
                schema = setOf(
                    MusicalCompositionRealm::class,
                    NotePairRealm::class,
                    NoteRealm::class
                )
            )
            .inMemory()
            .name("test-realm")
            .build()

        realm = Realm.open(config)
        dataBaseRepository = DataBaseRepositoryImpl(realm)
    }

    @Test
    fun saveMusicComposition_savesCorrectly() = runTest {
        val notes = listOf(
            Note(11, true, false),
            Note(33, false, false)
        )
        val notePairs = listOf(NotePairs(notes, 1))
        val composition = MusicalComposition("Test Title", notePairs)

        val result = dataBaseRepository.saveMusicComposition(composition)

        assertTrue(result)

        val allCompositions = dataBaseRepository.getAllMusicCompositions()

        assertEquals(1, allCompositions.size)
        assertEquals("Test Title", allCompositions[0].title)
        assertEquals(1, allCompositions[0].notesPairs.size)
    }

    @Test
    fun deleteMusicComposition_deletesCorrectly()  = runTest {
        val notes = listOf(
            Note(11, true, false),
            Note(33, false, false)
        )
        val notePairs = listOf(NotePairs(notes, 1))
        val composition = MusicalComposition("Test Title", notePairs)

        dataBaseRepository.saveMusicComposition(composition)

        val deleteResult = dataBaseRepository.deleteMusicComposition("Test Title")

        assertTrue(deleteResult)

        val allCompositions = dataBaseRepository.getAllMusicCompositions()

        assertTrue(allCompositions.isEmpty())
    }

    @Test
    fun getAllMusicCompositions_returnsEmptyListIfNoneSaved() = runTest {
        val allCompositions = dataBaseRepository.getAllMusicCompositions()

        assertTrue(allCompositions.isEmpty())
    }

    @After
    fun teardown() {
        realm.close()
    }
}