package com.cmp.microhabit.ui.screen.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cmp.microhabit.ui.screen.home.viewmodel.HomeViewmodel
import com.cmp.microhabit.utils.SetVerticalGap

@Composable
fun HomeScreen(viewmodel: HomeViewmodel) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f))
            .safeContentPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ,
        verticalArrangement = Arrangement.Top,
    ) {
        HomeProgressIndicator(viewmodel)
        SetVerticalGap(25)
        HabitGarden(viewmodel)
        SetVerticalGap(100)
    }
}