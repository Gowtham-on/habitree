package com.cmp.microhabit.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SetVerticalGap(height: Int) {
    Spacer(modifier = Modifier.height(height.dp))
}

@Composable
fun SetHorizontalGap(width: Int) {
    Spacer(modifier = Modifier.width(width.dp))
}