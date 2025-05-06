package com.cmp.microhabit.utils

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty

@Composable
fun LottieAnimationView(
    @RawRes resId: Int,
    height: Int = 100,
    width: Int = 100
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resId))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
            .width(width.dp)
    )
}

@Composable
fun ShowLottieWithIterations(
    @RawRes animationRes: Int,
    modifier: Modifier = Modifier,
    iterations: Int,
    isPlayingDone: () -> Unit? = {},
    play: Boolean = true,
    height: Int = 100,
    color: Int = MaterialTheme.colorScheme.primary.toArgb(),
    speed: Float = 1f
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = iterations,
        isPlaying = play,
        speed = speed,
    )

    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR_FILTER,
            value = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                color,
                BlendModeCompat.SRC_ATOP
            ),
            keyPath = arrayOf(
                "**"
            )
        )
    )

    val isPlaying = progress < 1f
    LaunchedEffect(isPlaying) {
        if (!isPlaying) {
            isPlayingDone()
        }
    }
    if (play) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = modifier.height(height.dp),
        )
    } else {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = modifier.height(height.dp),
            dynamicProperties = dynamicProperties
        )
    }

}
