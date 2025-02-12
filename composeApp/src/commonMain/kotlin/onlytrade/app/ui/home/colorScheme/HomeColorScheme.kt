package onlytrade.app.ui.home.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


interface HomeColorScheme {
    val screenBG: Color
    val botBarBG: Color
    val pagerCardBG: Color
    val topSearchBarBG: Color
}


val homeColorScheme @Composable
get() = if(isSystemInDarkTheme()) DarkHomeColorScheme else LightHomeColorScheme