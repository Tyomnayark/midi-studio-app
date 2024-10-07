package com.tyom.domain.usecases

import com.tyom.domain.repository.SettingsRepository
import javax.inject.Inject

class SetAutoConnectUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend fun execute(isAutoConnect: Boolean) {
        settingsRepository.setAutoConnect(isAutoConnect)
    }
}