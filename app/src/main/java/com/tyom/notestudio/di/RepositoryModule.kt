package com.tyom.notestudio.di

import com.tyom.domain.repository.InitRepository
import com.tyom.model.repository.InitRepositoryImpl
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
    fun provideInitRepository(initRepositoryImpl: InitRepositoryImpl): InitRepository {
        return initRepositoryImpl
    }
}