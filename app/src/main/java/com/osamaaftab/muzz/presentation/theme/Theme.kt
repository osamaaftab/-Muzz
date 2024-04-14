package com.osamaaftab.muzz.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.osamaaftab.muzz.R


private val darkColorScheme = darkColorScheme(
    primary = HotPink50,
    secondary = HotPinkDark50,
    tertiary = Pink40,
)

private val lightColorScheme = lightColorScheme(
    primary = HotPink100,
    secondary = HotPinkDark100,
    tertiary = Pink80,
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F))

private val openSansFontFamily = FontFamily(
    Font(R.font.opensans_regular),
    Font(R.font.opensans_regular, FontWeight.Normal),
    Font(R.font.opensans_medium, FontWeight.Medium),
    Font(R.font.opensans_bold, FontWeight.Bold),
)

val MuzzTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 42.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 42.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp
    ),
    labelLarge = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp
    ),
    labelSmall = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp
    )
)

@Composable
fun MuzzTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MuzzTypography,
        content = content
    )
}