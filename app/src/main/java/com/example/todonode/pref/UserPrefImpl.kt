package com.example.todonode.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPrefImpl(
    private val dataStore: DataStore<Preferences>
): UserPref {
    override fun getUserToken(): Flow<String> {
        return dataStore.data.catch { emit(emptyPreferences()) }.map {
            it[USER_KEY] ?: ""
        }
        //return dataStore.data.first()[USER_KEY]
//        return dataStore.data.catch { emit(emptyPreferences()) }.map {
//            it[USER_KEY]
//        }.first()
    }

    override suspend fun saveUserToken(token: String) {
        dataStore.edit {
            it[USER_KEY] = token
        }
    }

    override suspend fun clearUserToken() {
        dataStore.edit {
            it.clear()
        }
    }

}