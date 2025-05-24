package com.cmp.microhabit.ui.screen.onboarding.model

import com.cmp.microhabit.ui.screen.onboarding.utils.HabitPreferenceTime
import com.cmp.microhabit.ui.screen.onboarding.utils.HabitStoppingReason
import kotlinx.serialization.Serializable

@Serializable
data class OnboardingData(
    val habitType: List<HabitSelection>,
    val practiceDuration: Int,
    val habitPreferenceTime: HabitPreferenceTime,
    val habitStoppingReason: List<HabitStoppingReason>
)

@Serializable
data class HabitSelection (
    val id: Long = -1L,
    val name: String = "",
    var preferenceTime: Int = 0,
    val logs: List<HabitLog> = emptyList()
)

data class UserData (
    val id: Long = -1,
    val userName: String = "",
    val habitPreference: List<HabitSelection> = listOf<HabitSelection>(),
    val habitStoppingReason: List<HabitStoppingReason> = listOf<HabitStoppingReason>(),
    val habitPrefTime: HabitPreferenceTime = HabitPreferenceTime.NONE,
)

@Serializable
data class HabitLog(
    val habitId: Long = -1L,
    val dateString: String = "",
    val date: String = "",
    val completed: Boolean = false,
    val timestamp: Long = 0L,
    val streak: Long = 0L
)