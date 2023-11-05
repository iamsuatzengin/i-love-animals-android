package com.suatzengin.iloveanimals.data.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.suatzengin.iloveanimals.util.extension.EMPTY_STRING
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

    val accessToken: String? = runBlocking {
        runCatching {
            context.dataStore.data.first()[jwtKey]
        }.getOrElse {
            EMPTY_STRING
        }
    }

    fun logout() = runBlocking {
        context.dataStore.edit {
            it.remove(jwtKey)
        }
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = "auth_handler"
        )
    }
}