package com.tyom.notestudio.di

import com.tyom.data.models.MusicalCompositionRealm
import com.tyom.data.models.NotePairRealm
import com.tyom.data.models.NoteRealm
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton


private const val CURRENT_SCHEMA_VERSION = 1L

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    private val config = RealmConfiguration
        .Builder(
            schema = setOf(
                MusicalCompositionRealm::class,
                NotePairRealm::class,
                NoteRealm::class
            )
        )
        .schemaVersion(CURRENT_SCHEMA_VERSION)
        .build()

    @Provides
    @Singleton
    fun provideRealm(): Realm {
        return Realm.open(config)
    }
}
