package onlytrade.app.ui.onboarding.obPage3.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

interface OB3ColorScheme {
    val screenBG: Color
    val obLogoBar: Color
    val getStartedBtn: Color
    val loginBtn: Color
    val topBarBG: Color
    val botBarBG: Color
}


val ob3ColorScheme @Composable
get() = if(isSystemInDarkTheme()) DarkOB3ColorScheme else LightOB3ColorScheme