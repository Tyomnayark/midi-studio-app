package com.tyom.model.repository

import com.tyom.domain.models.Instrument
import com.tyom.domain.repository.InitRepository
import javax.inject.Inject

class InitRepositoryImpl @Inject constructor(): InitRepository {
    override suspend fun checkHaveConnectedInstrument(): Instrument? {
        // TODO:  @Tyom [8/26/24] { нужно из Preferences достать название инструмента }
        return null
    }
}