package com.example.quotes.presentation.core.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.quotes.presentation.core.ui.theme.LocalAppThemeColors

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    val appThemeColors = LocalAppThemeColors.current
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = appThemeColors.primary)
    }
}