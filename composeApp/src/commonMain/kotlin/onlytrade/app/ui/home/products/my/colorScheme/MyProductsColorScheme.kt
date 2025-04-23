package onlytrade.app.ui.home.products.my.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


interface MyProductsColorScheme {
    val screenBG: Color
    val botBarBG: Color
    val myProductsBarBG: Color
    val buySellTabBGOutline: Color
    val buySellTabBG: Color
    val buyTxt: Color
    val buyTxtClicked: Color
    val sellTxt: Color
    val sellTxtClicked: Color
    val buyBG: Color
    val buyBGClicked: Color
    val sellBG: Color
    val sellBGClicked: Color
}


val myProductsColorScheme
    @Composable
    get() = if (isSystemInDarkTheme()) DarkMyProductsColorScheme else LightMyProductColorScheme