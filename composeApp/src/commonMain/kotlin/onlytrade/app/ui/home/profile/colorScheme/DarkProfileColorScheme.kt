package onlytrade.app.ui.home.profile.colorScheme

import androidx.compose.ui.graphics.Color



object DarkProfileColorScheme: ProfileColorScheme {
    override val screenBG: Color
        get() = Color(0xFF101D25)
    override val botBarBG: Color
        get() = Color(0xFF232D36)
    override val topBarBG: Color
        get() = Color(0xF2232D36)
    override val myOffersBtn: Color
        get() = Color(0xFFD7F0D8)
    override val activeTradesBtn: Color
        get() = Color(0xFFD7F0D8)
    override val logoutBtn: Color
        get() = Color(0xFF9AD1D4)
    override val loginBtn: Color
        get() = Color(0xFF232D36)
}