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
import com.cmp.microhabit.utils.TimeUtils
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

    fun getSelectedHabitId(): Int {
        return selectedHabit.value.habitId
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
        loadLogsForHabit(habitId)
    }

    fun completeTodayTask(habitId: String, date: String, isCompleted: Boolean) {
        val currentStatisticsDetails = _habitStatistics.value?.get(habitId) ?: Statistics()

        val bestStreak =
            if (currentStatisticsDetails.currentStreak >= currentStatisticsDetails.bestStreak) {
                currentStatisticsDetails.currentStreak
            } else {
                currentStatisticsDetails.bestStreak
            }

        val isDoneYesterday = _logsList.value[habitId]?.dateLogs?.contains(
            TimeUtils.getYesterday()
        ) == true

        val currentStreak = if (isDoneYesterday)
            (_habitStatistics.value?.get(habitId)?.currentStreak ?: 0) + 1
        else 0

        repo.completeTask(
            habitId,
            userId,
            date,
            isCompleted,
            currentStreak,
            bestStreak,
            currentStatisticsDetails.noOfTimesCompleted,
            _logsList.value
        ) { isUpdated, type ->
            if (isUpdated) {
                // Update the log
                val oldHabitLog = _logsList.value[habitId]
                if (oldHabitLog != null) {
                    val updatedDateLogs = oldHabitLog.dateLogs.toMutableMap()
                    updatedDateLogs[date] = isCompleted
                    val updatedHabitLog = oldHabitLog.copy(dateLogs = updatedDateLogs)
                    val updatedLogsList = _logsList.value.toMutableMap()
                    updatedLogsList[habitId] = updatedHabitLog
                    _logsList.value = updatedLogsList
                }

                // Update Statistics
                val oldStatistics = _habitStatistics.value?.get(habitId)
                if (oldStatistics != null) {
                    val updatedStatistics = oldStatistics.copy(
                        currentStreak = oldStatistics.currentStreak + 1,
                        noOfTimesCompleted = oldStatistics.noOfTimesCompleted + 1,
                        bestStreak = maxOf(
                            oldStatistics.bestStreak,
                            oldStatistics.currentStreak + 1
                        )
                    )
                    val updatedHabitStatistics = _habitStatistics.value?.toMutableMap()
                    updatedHabitStatistics?.put(habitId, updatedStatistics)
                    _habitStatistics.value = updatedHabitStatistics
                }

                // Update Streak Chart
                val oldChartDetails = _chartData.value[habitId]
                if (oldChartDetails != null) {
                    val updatedStreaks = oldChartDetails.streaks.toMutableMap()
                    updatedStreaks[date] =
                        (_habitStatistics.value?.get(habitId)?.currentStreak ?: 0) + 1
                    val updatedChartDetails = oldChartDetails.copy(streaks = updatedStreaks)
                    val updatedChartData = _chartData.value.toMutableMap()
                    updatedChartData[habitId] = updatedChartDetails
                    _chartData.value = updatedChartData // Assign a new map
                }
            }
        }
    }

    fun addReflectionNotes(
        habitId: String,
        date: String,
        notes: String,
        onResult: (isSuccess: Boolean) -> Unit
    ) {
        repo.addReflectionNotes(
            habitLogs = _logsList.value,
            habitId = habitId,
            date = date,
            notes = notes,
            userId = userId
        ) { isSuccess, type ->
            onResult(isSuccess)
        }
    }


}