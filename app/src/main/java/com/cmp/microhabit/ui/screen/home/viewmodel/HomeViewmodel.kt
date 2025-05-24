package com.cmp.microhabit.ui.screen.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.cmp.microhabit.ui.screen.home.repository.HomeRepository
import com.cmp.microhabit.ui.screen.onboarding.model.HabitLog
import com.cmp.microhabit.ui.screen.onboarding.model.HabitSelection
import com.cmp.microhabit.utils.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val repo: HomeRepository,
) : ViewModel() {

    private var _progressPercentage = mutableFloatStateOf(0f)
    val progressPercentage: State<Float> get() = _progressPercentage

    fun setProgress(value: Float) {
        _progressPercentage.floatValue = value
    }

    private var _selectedHabit = mutableStateOf<HabitSelection>(HabitSelection())
    val selectedHabit: State<HabitSelection> get() = _selectedHabit

    fun setSelectedHabit(value: HabitSelection) {
        _selectedHabit.value = value
    }

    private val _logs = mutableStateOf<Map<String, Map<String, HabitLog>>>(emptyMap())
    val logs: State<Map<String, Map<String, HabitLog>>> get() = _logs

    fun loadLogsFromLastWeek(userId: String, habitId: String) {
        if (_logs.value.containsKey(habitId)) return // Already cached

        getLogsFromLastWeek(userId, habitId) { logs ->
            val map = logs?.associateBy { it.date.toString() } ?: emptyMap()
            _logs.value = _logs.value.toMutableMap().apply { put(habitId, map) }
        }
    }

    fun setHabitDone(userId: String, habitId: String, onResult: (Boolean) -> Unit) {
        var yesterdayLog = logs.value[habitId]
        var log = yesterdayLog?.get(TimeUtils.getYesterday("dd"))
        var streak = 0L
        if (log?.completed == true) {
            streak = log.streak + 1
        }

        repo.addHabitLog(
            userId = userId,
            completed = true,
            habitId = habitId.toString(),
            dateString = "2025-05-${TimeUtils.getToday("dd")}",
            date = TimeUtils.getToday("dd"),
            streak = streak,
            onResult = onResult
        )
    }

    fun getLogsFromLastWeek(userId: String, habitId: String, onResult: (List<HabitLog>?) -> Unit) {
        repo.getLogsFromPreviousSundayToToday(
            userId = userId,
            habitId = habitId,
            onResult = onResult
        )
    }




}