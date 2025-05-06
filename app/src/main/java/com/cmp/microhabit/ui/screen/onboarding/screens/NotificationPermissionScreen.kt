package com.cmp.microhabit.ui.screen.onboarding.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.cmp.microhabit.R
import com.cmp.microhabit.ui.component.button.MhButton
import com.cmp.microhabit.ui.component.text.ConstructText
import com.cmp.microhabit.utils.LottieAnimationView
import com.cmp.microhabit.utils.SetVerticalGap

@Composable
fun NotificationPermissionScreen(onComplete: () -> Unit) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onComplete()
        } else {
            onComplete()
        }
    }

    BaseOnboardingScreen {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            ConstructText(
                stringResource(R.string.stay_on_track),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            SetVerticalGap(15)
            ConstructText(
                stringResource(R.string.permission_reminder),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
            SetVerticalGap(30)
            LottieAnimationView(R.raw.notification_lottie, height = 250)
            SetVerticalGap(100)
            MhButton(
                name = stringResource(R.string.allow_notifications),
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        onComplete()
                    }
                },
                fillColor = MaterialTheme.colorScheme.primary,
                paddingHorizontal = 14,
                paddingVertical = 12,
                borderRadius = 50,
                fillMaxWidth = true,
                textColor = Color.White,
                textAlignment = TextAlign.Center,
                textStyle = MaterialTheme.typography.bodyMedium,
            )
            SetVerticalGap(10)
            MhButton(
                name = stringResource(R.string.skip_for_now),
                onClick = onComplete,
                paddingHorizontal = 14,
                paddingVertical = 12,
                borderRadius = 50,
                fillMaxWidth = false,
                textAlignment = TextAlign.Center,
                textStyle = MaterialTheme.typography.bodySmall,
            )
        }

    }
}