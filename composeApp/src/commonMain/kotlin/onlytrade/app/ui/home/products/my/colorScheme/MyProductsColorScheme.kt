package onlytrade.app.ui.home.products.my.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


interface MyProductsColorScheme {
    val screenBG: Color
    val botBarBG: Color
    val myProductsBarBG: Color
}


val myProductsColorScheme
    @Composable
    get() = if (isSystemInDarkTheme()) DarkMyProductsColorScheme else LightMyProductColorScheme