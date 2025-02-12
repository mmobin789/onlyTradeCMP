package onlytrade.app.ui.login.forgotPassword.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

interface ForgotPassColorScheme {
    val screenBG: Color
    val sendBtn: Color
}


val forgotPassColorScheme @Composable
get() = if(isSystemInDarkTheme()) DarkForgotPassColorScheme else LightForgotPassColorScheme