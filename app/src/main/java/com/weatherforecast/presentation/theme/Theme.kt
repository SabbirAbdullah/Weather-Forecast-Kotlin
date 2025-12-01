package com.weatherforecast.presentation.theme

import Purple200
import Purple500
import Teal200
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Purple500,
    secondary = Teal200,
    tertiary = Purple200
)

@Composable
fun WeatherTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),   // <-- This is Material 3 Typography
        shapes = Shapes(),
        content = content
    )
}
