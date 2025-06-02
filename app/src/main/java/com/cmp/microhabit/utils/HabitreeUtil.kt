package com.cmp.microhabit.utils

import androidx.annotation.RawRes
import com.cmp.microhabit.R

enum class LottieType(val key : String, @RawRes val resId : Int) {
    MINDFULNESS("habit_fitness",  R.raw.mindfulness_lottie),
    FITNESS("habit_fitness",  R.raw.workout_lottie),
    LEARNING_PROD("habit_fitness",  R.raw.workout_lottie),
    SLEEP("habit_fitness",  R.raw.workout_lottie),
    CREATIVE_HABITS("habit_fitness",  R.raw.idea_lottie),
    CAREER_GROWTH("habit_fitness",  R.raw.workout_lottie),
}

enum class RepositoryType {
    LOG,
    STATISTICS,
    CHART_LOG,
}