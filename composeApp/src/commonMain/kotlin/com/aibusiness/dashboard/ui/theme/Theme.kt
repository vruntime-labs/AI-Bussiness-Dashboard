package com.aibusiness.dashboard.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BusinessBlue = Color(0xFF1E56A0)
private val BusinessBlueDark = Color(0xFF163172)

private val LightColors = lightColorScheme(
    primary = BusinessBlue,
    secondary = BusinessBlueDark
)

private val DarkColors = darkColorScheme(
    primary = BusinessBlue,
    secondary = BusinessBlueDark
)

@Composable
fun DashboardTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
