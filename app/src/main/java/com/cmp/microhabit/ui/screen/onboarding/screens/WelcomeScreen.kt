package com.cmp.microhabit.ui.screen.onboarding.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.cmp.microhabit.R
import com.cmp.microhabit.ui.component.button.ButtonAnimation
import com.cmp.microhabit.ui.component.button.MhButton
import com.cmp.microhabit.ui.component.text.ConstructText
import com.cmp.microhabit.utils.LottieAnimationView
import com.cmp.microhabit.utils.SetVerticalGap

@Composable
fun WelcomeScreen(onNext: () -> Unit) {
    BaseOnboardingScreen {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Image(
//                    painter = painterResource(id = R.drawable.habitree_logo_app),
//                    contentDescription = "Onboarding Logo",
//                    modifier = Modifier.width(75.dp).height(75.dp)
//                )
                SetVerticalGap(30)
                ConstructText(
                    stringResource(R.string.grow_with_small_steps),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )
                SetVerticalGap(16)
                ConstructText(
                    stringResource(R.string.build_lasting_habits),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                LottieAnimationView(R.raw.welcome_lottie, height = 400)
                MhButton(
                    name = stringResource(R.string.get_started),
                    onClick = {
                        onNext()
                    },
                    textColor = Color.White,
                    fillColor = MaterialTheme.colorScheme.primary,
                    borderRadius = 50,
                    padding = 12,
                    animation = ButtonAnimation.SPRING,
                    textStyle = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.W700,
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(onNext = {})
}