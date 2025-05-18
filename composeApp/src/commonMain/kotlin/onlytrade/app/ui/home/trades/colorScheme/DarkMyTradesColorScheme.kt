package onlytrade.app.ui.home.trades.colorScheme

import androidx.compose.ui.graphics.Color


object DarkMyTradesColorScheme: MyTradesColorScheme {
    override val screenBG: Color
        get() = Color(0xFF101D25)
    override val botBarBG: Color
        get() = Color(0xFF232D36)
    override val myTradesBarBG: Color
        get() = Color(0xF2232D36)
    override val sentReceivedTabOutline: Color
        get() = Color(0xF2232D36)
}