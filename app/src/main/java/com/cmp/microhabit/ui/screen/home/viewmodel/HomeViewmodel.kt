package com.cmp.microhabit.ui.screen.home.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.lifecycle.ViewModel
import com.cmp.microhabit.ui.screen.home.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repo: HomeRepository,
) : ViewModel() {

    private var _progressPercentage = mutableFloatStateOf(0f)
    val progressPercentage: State<Float> get() = _progressPercentage

    fun setProgress(value: Float) {
        _progressPercentage.floatValue = value
    }

    fun setHabitDone(userId: String, habitId: String, onResult: (Boolean) -> Unit) {
        repo.addHabitLog(
            userId = userId,
            completed = false,
            habitId = habitId.toString(),
            date = "2025-05-26",
            onResult = onResult
        )
    }
}