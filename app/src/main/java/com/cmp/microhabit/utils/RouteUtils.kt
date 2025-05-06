package com.cmp.microhabit.utils

object OnboardingRoutes {
    const val WELCOME = "welcome"
    const val GOAL = "goal"
    const val TIME = "time"
    const val PERSONALIZATION = "personalization"
    const val NOTIFICATIONS = "notifications"
}


object AppRoutes {
    const val HOME = "home"
    const val HABIT_DETAILS = "habitDetails"
    const val INSIGHTS = "insights"
    const val PROFILE = "profile"
}

data class BottomTabItems(
    val route: String,
    val selectedIcon: Int,
    val canShowDot: Boolean,
    val badgeCount: Int? = null,
    val lottieSpeed: Float = 1f
)

