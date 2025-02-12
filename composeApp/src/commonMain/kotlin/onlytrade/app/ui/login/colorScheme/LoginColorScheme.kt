package onlytrade.app.ui.login.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

interface LoginColorScheme {
    val screenBG: Color
    val loginBtn: Color
}


val loginColorScheme @Composable
get() = if(isSystemInDarkTheme()) DarkLoginColorScheme else LightLoginColorScheme