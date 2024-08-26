package com.tyom.domain.usecases

import com.tyom.domain.models.Instrument
import com.tyom.domain.repository.InitRepository
import javax.inject.Inject

class CheckHaveConnectedInstrumentUseCase @Inject constructor(
    private val initRepository: InitRepository
) {
    suspend fun checkHaveConnectedInstrument(): Instrument? {
        return initRepository.checkHaveConnectedInstrument()
    }
}