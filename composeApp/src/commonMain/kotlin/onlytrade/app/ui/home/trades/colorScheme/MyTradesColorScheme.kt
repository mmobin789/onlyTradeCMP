package onlytrade.app.ui.home.trades.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


interface MyTradesColorScheme {
    val screenBG: Color
    val botBarBG: Color
    val myTradesBarBG: Color
    val sentReceivedTabOutline: Color
}


val myTradesColorScheme @Composable
get() = if(isSystemInDarkTheme()) DarkMyTradesColorScheme else LightMyTradesColorScheme