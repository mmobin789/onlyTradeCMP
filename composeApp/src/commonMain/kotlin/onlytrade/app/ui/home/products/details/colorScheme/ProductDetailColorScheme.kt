package onlytrade.app.ui.home.products.details.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


interface ProductDetailColorScheme {
    val screenBG: Color
    val productTagsBG: Color
    val offerTradeBtn: Color
    val offerTradeBtnText: Color
    val offerTradeBtnBorder: Color
    val buyProductBtn: Color
}


val productDetailColorScheme @Composable
get() = if(isSystemInDarkTheme()) DarkProductDetailColorScheme else LightProductDetailColorScheme