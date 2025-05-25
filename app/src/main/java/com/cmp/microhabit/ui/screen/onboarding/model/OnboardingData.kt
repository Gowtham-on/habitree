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
data class HabitSelection(
    val id: Long = -1L,
    val name: String = "",
    var preferenceTime: Int = 0,
)

data class UserData(
    val id: Long = -1,
    val userName: String = "",
    val habitStoppingReason: List<HabitStoppingReason> = listOf<HabitStoppingReason>(),
)

data class UserHabit(
    val habitName: String = "",
    val habitId: Int = -1,
    val timeForHabit: Int = 0
)

data class HabitLog (
    val dateLogs: Map<String, Boolean> = mapOf(),
)

data class Statistics (
    val currentStreak: Int = 0,
    val noOfTimesCompleted: Int = 0,
    val bestStreak: Int = 0,
)