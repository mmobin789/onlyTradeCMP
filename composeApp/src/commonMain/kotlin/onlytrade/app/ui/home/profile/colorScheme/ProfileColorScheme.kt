package onlytrade.app.ui.home.profile.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color



interface ProfileColorScheme {
    val screenBG: Color
    val botBarBG: Color
    val topBarBG: Color
    val activeTradesBtn: Color
    val myOffersBtn: Color
    val logoutBtn: Color
}


val profileColorScheme @Composable
get() = if(isSystemInDarkTheme()) DarkProfileColorScheme else LightProfileColorScheme