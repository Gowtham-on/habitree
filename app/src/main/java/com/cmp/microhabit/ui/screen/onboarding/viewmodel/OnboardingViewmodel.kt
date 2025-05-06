package com.cmp.microhabit.ui.screen.onboarding.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmp.microhabit.ui.screen.onboarding.model.HabitSelection
import com.cmp.microhabit.ui.screen.onboarding.model.OnboardingData
import com.cmp.microhabit.ui.screen.onboarding.utils.HabitPreferenceTime
import com.cmp.microhabit.ui.screen.onboarding.utils.HabitStoppingReason
import com.cmp.microhabit.ui.screen.onboarding.utils.OnboardingPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewmodel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    init {
        getOnBoardingData(context)
    }

    private val _focusSelection = mutableStateListOf<HabitSelection>()
    val focusSelection: List<HabitSelection> get() = _focusSelection

    fun addFocusSelection(value: HabitSelection) {
        _focusSelection.add(value)
    }

    fun removeFocusSelection(value: HabitSelection) {
        _focusSelection.remove(value)
    }

    private val _timeSelection = mutableIntStateOf(-1)
    val timeSelection: State<Int> get() = _timeSelection

    fun addTimeSelection(value: Int) {
        _timeSelection.intValue = value
    }

    private val _habitPreferenceTime = mutableStateOf<HabitPreferenceTime>(HabitPreferenceTime.NONE)
    val habitPreferenceTime: State<HabitPreferenceTime> = _habitPreferenceTime

    fun setHabitPreferenceTime(value: HabitPreferenceTime) {
        _habitPreferenceTime.value = value
    }

    private val _habitStoppingReason = mutableStateListOf<HabitStoppingReason>()
    val habitStoppingReason: List<HabitStoppingReason> = _habitStoppingReason

    fun addHabitStoppingReason(value: HabitStoppingReason) {
        _habitStoppingReason.add(value)
    }

    fun removeHabitStoppingReason(value: HabitStoppingReason) {
        _habitStoppingReason.remove(value)
    }

    val onBoardingCompleted: StateFlow<Boolean?> =
        OnboardingPreferences.getOnboardingCompleted(context).stateIn(
            viewModelScope,
            SharingStarted.Eagerly, null
        )

    fun setOnboardingDone() {
        val onboardingData = OnboardingData(
            habitType = focusSelection,
            practiceDuration = timeSelection.value,
            habitPreferenceTime = habitPreferenceTime.value,
            habitStoppingReason = habitStoppingReason
        )

        viewModelScope.launch {
            OnboardingPreferences.setOnboardingCompleted(context, completed = true, onboardingData)
        }
    }


    fun getOnBoardingData(context: Context) {
        viewModelScope.launch {
            OnboardingPreferences.getOnboardingData(context).collect {
                if (it == null) return@collect
                _focusSelection.clear()
                _focusSelection.addAll(it.habitType)
                _timeSelection.intValue = it.practiceDuration
                _habitPreferenceTime.value = it.habitPreferenceTime
                _habitStoppingReason.addAll(it.habitStoppingReason)
            }
        }
    }
}
