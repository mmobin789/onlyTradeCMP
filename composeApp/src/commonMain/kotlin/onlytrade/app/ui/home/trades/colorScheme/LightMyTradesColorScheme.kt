package onlytrade.app.ui.home.trades.colorScheme

import androidx.compose.ui.graphics.Color


object LightMyTradesColorScheme : MyTradesColorScheme {
    override val screenBG: Color
        get() = Color(0xFFD2D3BD)
    override val botBarBG: Color
        get() = Color(0xFFFAFAFA)
    override val myTradesBarBG: Color
        get() = Color(0xFFB26560)
    override val sentReceivedTabOutline: Color
        get() = Color(0xFFB26560)
}