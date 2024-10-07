package com.tyom.domain.repository

interface SettingsRepository {
    suspend fun checkIsAutoConnect(): Boolean

    suspend fun setAutoConnect(isAutoConnect: Boolean)
}