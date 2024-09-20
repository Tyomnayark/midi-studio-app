package com.tyom.notestudio.di

import com.tyom.data.repository.MIDIRepositoryImpl
import com.tyom.domain.repository.MIDIRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMidiRepository(midiRepositoryImpl: MIDIRepositoryImpl): MIDIRepository {
        return midiRepositoryImpl
    }
}