package com.example.todonode.pref

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

val USER_KEY = stringPreferencesKey("user_token")

interface UserPref {

    fun getUserToken(): Flow<String>

    suspend fun saveUserToken(token: String)

    suspend fun clearUserToken()

}