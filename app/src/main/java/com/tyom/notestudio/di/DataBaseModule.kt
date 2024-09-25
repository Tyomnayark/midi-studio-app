package com.tyom.notestudio.di

import com.tyom.data.models.MusicalComposition
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    private val configuration = RealmConfiguration.create(schema = setOf(MusicalComposition::class))

    @Provides
    @Singleton
    fun provideRealm(): Realm {
        return Realm.open(configuration)
    }
}
