package com.tyom.domain.usecases

import com.tyom.domain.models.Instrument
import com.tyom.domain.repository.InitRepository
import javax.inject.Inject

class CheckHaveConnectedInstrumentUseCase @Inject constructor(
    private val authorizationRepository: InitRepository
) {
    suspend fun checkHaveConnectedInstrument(): Instrument? {
        return authorizationRepository.checkHaveConnectedInstrument()
    }
}