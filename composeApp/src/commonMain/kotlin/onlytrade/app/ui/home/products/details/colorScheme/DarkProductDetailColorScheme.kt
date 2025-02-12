package onlytrade.app.ui.home.products.details.colorScheme

import androidx.compose.ui.graphics.Color


object DarkProductDetailColorScheme: ProductDetailColorScheme {
    override val screenBG: Color
        get() = Color(0xFF101D25)
    override val productTagsBG: Color
        get() = Color.Cyan
    override val offerTradeBtn: Color
        get() = Color(0xFF232D36)
    override val offerTradeBtnText: Color
        get() = Color(0xFFD7F0D8)
    override val offerTradeBtnBorder: Color
        get() = Color(0xFFD7F0D8)
    override val buyProductBtn: Color
        get() = Color(0xFFD7F0D8)
}