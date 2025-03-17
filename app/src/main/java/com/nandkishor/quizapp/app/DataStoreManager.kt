package com.nandkishor.quizapp.app

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("user_prefs")

class DataStoreManager (private val context: Context) {

    companion object {
        val DARK_MODE_KEY = booleanPreferencesKey("isDarkModeEnabled")
        val HIGHEST_SCORE = floatPreferencesKey("highestScore")
    }

    suspend fun updateThemePref(isDarkModeEnabled: Boolean) {
        context.dataStore.edit {
            it[DARK_MODE_KEY] = isDarkModeEnabled
        }
    }

    suspend fun updateScore(newScore: Float) {
        context.dataStore.edit {
            it[HIGHEST_SCORE] = newScore
        }
    }

    fun getThemeFlow(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[DARK_MODE_KEY] ?: false
        }
    }

    fun getHighestScore(): Flow<Float> {
        return context.dataStore.data.map {
            it[HIGHEST_SCORE] ?: 0f
        }
    }
}