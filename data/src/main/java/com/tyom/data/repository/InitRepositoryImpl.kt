package com.tyom.data.repository

import android.app.Application
import android.content.Context
import com.tyom.domain.models.Instrument
import com.tyom.domain.models.toInstrument
import com.tyom.domain.repository.InitRepository
import com.tyom.data.Preferences
import com.tyom.data.PrefsDictionary.INSTRUMENT
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class InitRepositoryImpl @Inject constructor(
    private var context: Context
): InitRepository {
    override suspend fun checkHaveConnectedInstrument(): Instrument? {
        return Preferences.getString(context, INSTRUMENT)?.toInstrument()
    }
}