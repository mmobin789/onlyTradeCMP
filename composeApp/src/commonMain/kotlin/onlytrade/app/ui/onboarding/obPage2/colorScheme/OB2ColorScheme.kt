package onlytrade.app.ui.onboarding.obPage2.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

interface OB2ColorScheme {
    val screenBG: Color
    val obLogoBar: Color
    val nextBtn: Color
    val topBarBG: Color
}


val ob2ColorScheme @Composable
get() = if(isSystemInDarkTheme()) DarkOB2ColorScheme else LightOB2ColorScheme