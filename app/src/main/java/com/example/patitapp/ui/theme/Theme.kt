package com.example.patitapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import com.example.patitapp.R

// -------------------------------------------------------------
// PALETA VERDE AGUA (aplicada siempre, sin dinámicos)
// -------------------------------------------------------------
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF33C9B5),
    onPrimary = Color.White,
    secondary = Color(0xFF6ADFCC),
    tertiary = Color(0xFF2F9E8C),
    background = Color(0xFF002B27),
    surface = Color(0xFF00332E)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF3CC9B5),
    onPrimary = Color.White,
    secondary = Color(0xFF7ADFCC),
    tertiary = Color(0xFF2AA493),
    surface = Color(0xFFF4FFFD),
    background = Color(0xFFF4FFFD),
    error = Color(0xFFB3261E)
)

// -------------------------------------------------------------
// FUENTE: POPPINS
// -------------------------------------------------------------
private val Poppins = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal)
)

private val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)

// -------------------------------------------------------------
// THEME PRINCIPAL (sin colores dinámicos)
// -------------------------------------------------------------
@Composable
fun PatitAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
