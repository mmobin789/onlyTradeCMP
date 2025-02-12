package onlytrade.app.ui.home.products.details.colorScheme

import androidx.compose.ui.graphics.Color


object LightProductDetailColorScheme : ProductDetailColorScheme {
    override val screenBG: Color
        get() = Color(0xFFFAFAFA)
    override val productTagsBG: Color
        get() = Color.Cyan
    override val offerTradeBtn: Color
        get() = Color(0xFFB26560)
    override val offerTradeBtnText: Color
        get() = Color(0xFFB26560)
    override val offerTradeBtnBorder: Color
        get() = Color(0xFFB26560)
    override val buyProductBtn: Color
        get() = Color(0xFFB26560)
}