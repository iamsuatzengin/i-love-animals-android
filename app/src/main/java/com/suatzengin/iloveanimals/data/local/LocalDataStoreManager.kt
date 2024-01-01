package com.suatzengin.iloveanimals.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.suatzengin.iloveanimals.data.auth.IlaAuthHandler.Companion.dataStore
import com.suatzengin.iloveanimals.di.dispatcher.Dispatcher
import com.suatzengin.iloveanimals.di.dispatcher.IlaDispatchers
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataStoreManager @Inject constructor(
    @ApplicationContext val context: Context,
    @Dispatcher(IlaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {

    val postalCode: Flow<String>
        get() = runBlocking(ioDispatcher) {
            context.dataStore.data.map { preferences ->
                preferences[postalCodeKey].orEmpty()
            }
        }

    suspend fun savePostalCode(postalCode: String) = withContext(ioDispatcher) {
        context.dataStore.edit { preferences ->
            preferences[postalCodeKey] = postalCode
        }
    }

    companion object {
        private val postalCodeKey = stringPreferencesKey("postal_code_key")
    }
}
