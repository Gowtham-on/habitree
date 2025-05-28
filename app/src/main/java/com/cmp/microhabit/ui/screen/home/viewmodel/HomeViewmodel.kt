package com.cmp.microhabit.ui.screen.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.cmp.microhabit.ui.screen.home.repository.HomeRepository
import com.cmp.microhabit.ui.screen.onboarding.model.HabitLog
import com.cmp.microhabit.ui.screen.onboarding.model.Statistics
import com.cmp.microhabit.ui.screen.onboarding.model.StreakChartDetails
import com.cmp.microhabit.ui.screen.onboarding.model.UserHabit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val repo: HomeRepository,
) : ViewModel() {

    var userId = ""

    private var _progressPercentage = mutableFloatStateOf(0f)
    val progressPercentage: State<Float> get() = _progressPercentage

    fun setProgress(value: Float) {
        _progressPercentage.floatValue = value
    }

    private var _selectedHabit = mutableStateOf<UserHabit>(UserHabit())
    val selectedHabit: State<UserHabit> get() = _selectedHabit

    fun setSelectedHabit(value: UserHabit) {
        _selectedHabit.value = value
    }

    private var _logsList = mutableStateOf<Map<String, HabitLog>>(mapOf())
    val logs: State<Map<String, HabitLog>> get() = _logsList

    fun loadLogsForHabit(habitId: String) {
        if (habitId == "-1") {
            return
        }
        if (_logsList.value.contains(habitId)) {
            return
        }
        repo.getRecentLogs(userId, habitId) { log ->
            if (log != null) {
                _logsList.value = _logsList.value.toMutableMap().apply {
                    put(habitId, log)
                }
            }
        }
    }

    private var _habitStatistics = mutableStateOf<Map<String, Statistics>?>(mapOf())
    val habitStatistics: State<Map<String, Statistics>?> get() = _habitStatistics

    fun loadHabitStatistics(habitId: String) {
        if (habitId.isEmpty() || habitId == "-1") {
            return
        }
        if (_habitStatistics.value?.contains(habitId) == true) {
            return
        }
        repo.getHabitStatistics(userId, habitId) { statistics ->
            if (statistics != null) {
                _habitStatistics.value = _habitStatistics.value?.toMutableMap()?.apply {
                    put(habitId, statistics)
                }
            }
        }
    }

    private val _habitList = mutableStateOf<List<UserHabit>>(listOf())
    var habitList: State<List<UserHabit>> = _habitList

    fun getHabitList() {
        if (userId.isEmpty()) return
        repo.getHabitList(userId) {
            _habitList.value = it
        }
    }

    private val _chartData = mutableStateOf<Map<String, StreakChartDetails>>(mapOf())
    val chartData: State<Map<String, StreakChartDetails>> get() = _chartData

    fun getStreakChartData(habitId: String) {
        if (habitId == "-1") {
            return
        }
        if (_chartData.value.contains(habitId)) {
            return
        }
        repo.getStreakChartData(userId, habitId) {

            if (it != null) {
                _chartData.value = _chartData.value.toMutableMap().apply {
                    put(habitId, it)
                }
            }
        }
    }


    fun getAllHabitDetails(habitId: String) {
        loadHabitStatistics(habitId)
        getStreakChartData(habitId)
    }

}