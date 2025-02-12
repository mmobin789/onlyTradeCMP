package onlytrade.app.ui.onboarding.obPage1.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

interface OB1ColorScheme {
    val screenBG: Color
    val obLogoBar: Color
    val nextBtn: Color
    val topBarBG: Color
}


val ob1ColorScheme @Composable
get() = if(isSystemInDarkTheme()) DarkOB1ColorScheme else LightOB1ColorScheme