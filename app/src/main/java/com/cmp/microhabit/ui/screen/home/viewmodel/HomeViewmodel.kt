package com.cmp.microhabit.ui.screen.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.cmp.microhabit.ui.screen.home.repository.HomeRepository
import com.cmp.microhabit.ui.screen.onboarding.model.HabitLog
import com.cmp.microhabit.ui.screen.onboarding.model.HabitSelection
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
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

    private val _logs = mutableStateOf<Map<String, HabitLog>>(emptyMap())
    val logs: State<Map<String, HabitLog>> get() = _logs

    fun loadLogsFromLastWeek(userId: String, habitId: String) {
        getLogsFromLastWeek(userId, habitId) { logs ->
            val map = logs?.associateBy { it.date.toString() }
            _logs.value = map ?: emptyMap()
        }
    }

    fun setHabitDone(userId: String, habitId: String, onResult: (Boolean) -> Unit) {
        repo.addHabitLog(
            userId = userId,
            completed = true,
            habitId = habitId.toString(),
            dateString = "2025-05-26",
            date = "14",
            onResult = onResult
        )
    }

    fun getLogsFromLastWeek(userId: String, habitId: String, onResult: (List<HabitLog>?) -> Unit) {
        val days = getDateRangeLastSundayToThisSaturday()
        repo.getLogsFromPreviousSundayToToday (
            userId = userId,
            habitId = habitId,
            startDate = days.first(),
            endDate = days.last(),
            onResult = onResult
        )
    }


    fun getDateRangeLastSundayToThisSaturday(): List<String> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)

        val lastSundayCal = cal.clone() as Calendar
        lastSundayCal.add(Calendar.DAY_OF_YEAR, -6)

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val result = mutableListOf<String>()

        val tempCal = lastSundayCal.clone() as Calendar
        while (!tempCal.after(cal)) {
            result.add(sdf.format(tempCal.time))
            tempCal.add(Calendar.DAY_OF_YEAR, 1)
        }
        return result
    }

}