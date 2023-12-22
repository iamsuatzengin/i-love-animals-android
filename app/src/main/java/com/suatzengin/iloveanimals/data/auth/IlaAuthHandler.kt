package com.suatzengin.iloveanimals.data.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.suatzengin.iloveanimals.di.dispatcher.Dispatcher
import com.suatzengin.iloveanimals.di.dispatcher.IlaDispatchers
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IlaAuthHandler @Inject constructor(
    @ApplicationContext val context: Context,
    @Dispatcher(IlaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {

    val isLogin
        get() = runBlocking {
            context.dataStore.data.first()[jwtKey].isNullOrEmpty().not()
        }

    suspend fun saveJWT(token: String) = withContext(ioDispatcher) {
        context.dataStore.edit { auth ->
            auth[jwtKey] = token
        }
    }

    val accessToken
        get() = runBlocking(ioDispatcher) {
            context.dataStore.data.first()[jwtKey].orEmpty()
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

        private val jwtKey = stringPreferencesKey("jwt_key")
    }
}
