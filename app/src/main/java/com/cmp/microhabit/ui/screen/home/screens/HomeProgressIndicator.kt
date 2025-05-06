package com.cmp.microhabit.ui.screen.home.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cmp.microhabit.ui.screen.home.viewmodel.HomeViewmodel

@Composable
fun HomeProgressIndicator(viewmodel: HomeViewmodel) {
    val floatProgress: Float by animateFloatAsState(
        targetValue = viewmodel.progressPercentage.value,
        animationSpec = tween(durationMillis = 1000)
    )
    Column(
        modifier =  Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box {
            CircularProgressIndicator(
                progress = { floatProgress },
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 12.dp,
                trackColor = Color.Gray,
                gapSize = Dp.Unspecified,
                strokeCap = StrokeCap.Round,
                modifier = Modifier
                    .size(200.dp)
            )
            Box(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    "${(floatProgress * 100).toInt()}%",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}