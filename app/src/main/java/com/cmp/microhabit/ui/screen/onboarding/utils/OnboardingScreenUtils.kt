package com.cmp.microhabit.ui.screen.onboarding.utils

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cmp.microhabit.ui.screen.onboarding.model.OnboardingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

val Context.dataStore by preferencesDataStore(name = "onboarding_prefs")

@Serializable
enum class HabitPreferenceTime {
    MORNING, AFTERNOON, EVENING, ANYTIME, NONE
}

@Serializable
enum class HabitStoppingReason {
    TIME, FORGETFULNESS, MOTIVATION, DISTRACTION, NONE
}

object OnboardingPreferences {
    private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    val ONBOARDING_JSON = stringPreferencesKey("onboarding_json")
    val USER_ID = stringPreferencesKey("user_id")

    suspend fun setOnboardingCompleted(
        context: Context,
        completed: Boolean,
        userData: OnboardingData
    ) {
        val json = Json.encodeToString(OnboardingData.serializer(), userData)

        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED] = completed
            preferences[ONBOARDING_JSON] = json
        }
    }

    suspend fun setUserId(context: Context, userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    fun getOnboardingCompleted(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { it[ONBOARDING_COMPLETED] == true }
    }

    fun getOnboardingData(context: Context): Flow<OnboardingData?> {
        return context.dataStore.data.map { prefs ->
            prefs[ONBOARDING_JSON]?.let { json ->
                try {
                    Json.decodeFromString<OnboardingData>(json)
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    fun getUserId(context: Context): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[USER_ID]
        }
    }
}

fun getHabitLottie(context: Context, habitName: String) {

}

