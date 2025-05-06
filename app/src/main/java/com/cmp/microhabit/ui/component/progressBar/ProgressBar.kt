package com.cmp.microhabit.ui.component.progressBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MhProgressBar(
    type: ProgressBarType,
    progress: Float,
    color: Color,
    backgroundColor: Color,
    thickness: Int? = null,
    gapSize: Int? = null,
    cornerRadius: Int? = null,
    width: Int? = null
) {
    Box(modifier = Modifier.width(width?.dp ?: 50.dp)) {
        if (type == ProgressBarType.CIRCULAR) {
            CircularProgressIndicator(
                progress = { progress },
                color = color,
                strokeWidth = thickness?.dp ?: 5.dp,
                trackColor = backgroundColor,
                gapSize = gapSize?.dp ?: Dp.Unspecified,
                strokeCap = StrokeCap.Round,
                modifier = Modifier
                    .width(width?.dp ?: 50.dp))
        } else {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .clip(RoundedCornerShape(cornerRadius?.dp ?: 10.dp))
                    .fillMaxWidth()
                    .background(color = backgroundColor)
                    .height(thickness?.dp ?: 1.dp),
                color = color,
                gapSize = gapSize?.dp ?: Dp.Unspecified
            )
        }
    }

}

enum class ProgressBarType {
    CIRCULAR,
    LINEAR
}

@Preview(showBackground = true)
@Composable
fun ProgressPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        MhProgressBar(
            type = ProgressBarType.CIRCULAR,
            progress = 0.5f,
            color = Color.Red,
            backgroundColor = Color.Blue,
            thickness = 10,
        )
    }

}