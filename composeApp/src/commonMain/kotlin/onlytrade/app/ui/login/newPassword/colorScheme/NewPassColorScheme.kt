package onlytrade.app.ui.login.newPassword.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

interface NewPassColorScheme {
    val screenBG: Color
    val saveBtn: Color
}


val newPassColorScheme @Composable
get() = if(isSystemInDarkTheme()) DarkNewPassColorScheme else LightNewPassColorScheme