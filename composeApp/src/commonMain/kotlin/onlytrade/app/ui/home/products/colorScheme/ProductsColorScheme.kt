package onlytrade.app.ui.home.products.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


interface ProductsColorScheme {
    val screenBG: Color
    val botBarBG: Color
    val topBarBG: Color
}


val productsColorScheme @Composable
get() = if(isSystemInDarkTheme()) DarkProductsColorScheme else LightProductsColorScheme