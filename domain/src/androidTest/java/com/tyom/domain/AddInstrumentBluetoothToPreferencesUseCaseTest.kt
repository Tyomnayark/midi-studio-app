package com.tyom.domain

import com.tyom.core_utils.extensions.empty
import com.tyom.domain.models.Instrument
import com.tyom.domain.repository.MIDIRepository
import com.tyom.domain.usecases.AddInstrumentBluetoothToPreferencesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class AddInstrumentBluetoothToPreferencesUseCaseTest {

    private lateinit var midiRepository: MIDIRepository
    private lateinit var addInstrumentBluetoothToPreferencesUseCase: AddInstrumentBluetoothToPreferencesUseCase

    @Before
    fun setUp() {
        midiRepository = mockk()
        addInstrumentBluetoothToPreferencesUseCase = AddInstrumentBluetoothToPreferencesUseCase(midiRepository)
    }

    @kotlin.OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    @Test
    fun executeShouldCallAddInstrumentBluetoothToPreferencesOnRepository() = runTest {
        val instrument = Instrument(name = "Piano d3e2", address = String.empty())
        println("Testing addInstrumentBluetoothToPreferences with instrument: $instrument")

        coEvery { midiRepository.addInstrumentBluetoothToPreferences(instrument) } answers {
            println("Called addInstrumentBluetoothToPreferences with $instrument")
        }
        addInstrumentBluetoothToPreferencesUseCase.execute(instrument)

        coVerify { midiRepository.addInstrumentBluetoothToPreferences(instrument) }
    }
}