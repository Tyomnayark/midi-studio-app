package com.tyom.data.repository

import android.content.Context
import com.tyom.data.Preferences
import com.tyom.data.PrefsDictionary.AUTOCONNECT
import com.tyom.domain.repository.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private var context: Context
): SettingsRepository {

    override suspend fun checkIsAutoConnect(): Boolean {
       return Preferences.getBoolean(context, AUTOCONNECT)
    }

    override suspend fun setAutoConnect(isAutoConnect: Boolean) {
        Preferences.putBoolean(context, AUTOCONNECT, isAutoConnect)
    }
}