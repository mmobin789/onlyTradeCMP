package onlytrade.app.ui.splash.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


interface SplashColorScheme {
    val screenBG: Color
    val appDesc: Color
}


val splashColorScheme @Composable
get() = if(isSystemInDarkTheme()) DarkSplashColorScheme else LightSplashColorScheme