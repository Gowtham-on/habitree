package com.cmp.microhabit.ui.screen.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(): ViewModel() {

    private var _progressPercentage = mutableFloatStateOf(0f)
    val progressPercentage: State<Float> get() = _progressPercentage

    fun setProgress(value: Float) {
        _progressPercentage.floatValue = value
    }
}