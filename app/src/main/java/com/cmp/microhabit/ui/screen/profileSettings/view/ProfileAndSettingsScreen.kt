package com.cmp.microhabit.ui.screen.profileSettings.view

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cmp.microhabit.R
import com.cmp.microhabit.utils.SetVerticalGap

@Composable
fun ProfileAndSettingsScreen() {
    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE6F8F1),
                        Color(0xFFC6F5DE)
                    )
                )
            )
            .padding(bottom = 7.dp)
            .padding(horizontal = 5.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
    ) {
        SetVerticalGap(15)
        Text(
            stringResource(R.string.profile_settings),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        SetVerticalGap(25)
        GetProfileSettingsText("📊 Your Stats")
        GetProfileSettingsText("🔔 Notification Settings")
        GetProfileSettingsText("🎨 Theme Customization")
        GetProfileSettingsText("📍 Context Reminder Settings")
        GetProfileSettingsText("🛡️ Account & Privacy")
    }
}

@Composable
fun GetProfileSettingsText(text: String) {
    val haptic = LocalView.current

    Column(
        modifier = Modifier.clickable(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            })
    ) {
        SetVerticalGap(14)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(5.dp)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "go",
                tint = Color.Gray,
                modifier = Modifier.padding(end = 10.dp)
            )
        }
        SetVerticalGap(14)
        GetDivider(1)
    }
}

@Composable
fun GetDivider(height: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
            .background(color = Color.Gray.copy(alpha = 0.3f)),
    )
}