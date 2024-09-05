package com.tyom.notestudio.di

import android.app.Application
import android.content.Context
import com.tyom.data.midi.BluetoothMidiInstrumentProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MidiModule {

    @Provides
    fun provideMidiInstrumentBluetoothProvider(
        @ApplicationContext context: Context,
    ): BluetoothMidiInstrumentProvider {
        return BluetoothMidiInstrumentProvider(context)
    }
}
