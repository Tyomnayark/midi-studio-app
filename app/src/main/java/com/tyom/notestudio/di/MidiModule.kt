package com.tyom.notestudio.di

import android.content.Context
import com.tyom.data.midi.MidiProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MidiModule {

    @Provides
    @Singleton
    fun provideMidiProvider(@ApplicationContext context: Context): MidiProvider {
        return MidiProvider(context)
    }
}
