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
    val id: Int,
    val name: String,
    var preferenceTime: Int
)

data class UserData (
    val id: Long,
    val userName: String,
    val habitPreference: List<HabitSelection>,
    val habitStoppingReason: List<HabitStoppingReason>,
    val habitPrefTime: HabitPreferenceTime
)