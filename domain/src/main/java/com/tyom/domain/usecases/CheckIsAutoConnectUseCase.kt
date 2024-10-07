package com.tyom.domain.usecases

import com.tyom.domain.repository.SettingsRepository
import javax.inject.Inject

class CheckIsAutoConnectUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend fun execute(): Boolean = settingsRepository.checkIsAutoConnect()
}