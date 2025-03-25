package com.nandkishor.quizapp.app

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("user_prefs")

class DataStoreManager (private val context: Context) {

    companion object {
        val DARK_MODE_KEY = stringPreferencesKey("isDarkModeEnabled")
        val HIGHEST_SCORE = doublePreferencesKey("highestScore")
    }

    suspend fun updateThemePref(isDarkModeEnabled: String) {
        context.dataStore.edit {
            it[DARK_MODE_KEY] = isDarkModeEnabled
        }
    }

    suspend fun updateScore(newScore: Double) {
        context.dataStore.edit {
            it[HIGHEST_SCORE] = newScore
        }
    }

    fun getThemeFlow(): Flow<String> {
        return context.dataStore.data.map {
            it[DARK_MODE_KEY] ?: "System default"
        }
    }

    fun getHighestScore(): Flow<Double> {
        return context.dataStore.data.map {
            it[HIGHEST_SCORE] ?: 0.0
        }
    }
}