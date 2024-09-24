package com.tyom.notestudio.di

import android.content.Context
import com.tyom.data.providers.FileSaveProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FileSaveModule {
    @Provides
    @Singleton
    fun provideFileSaveProvider(@ApplicationContext context: Context): FileSaveProvider {
        return FileSaveProvider(context)
    }
}