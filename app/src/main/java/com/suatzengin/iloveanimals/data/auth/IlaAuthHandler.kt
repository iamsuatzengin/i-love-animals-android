package com.suatzengin.iloveanimals.data.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class IlaAuthHandler @Inject constructor(
    @ApplicationContext val context: Context
) {

    private val jwtKey = stringPreferencesKey("jwt_key")

    val isLogin
        get() = runBlocking {
            context.dataStore.data.first()[jwtKey].isNullOrEmpty().not()
        }

    suspend fun saveJWT(token: String) {
        context.dataStore.edit { auth ->
            auth[jwtKey] = token
        }
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = "auth_handler"
        )
    }
}