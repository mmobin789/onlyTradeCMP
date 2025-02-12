package onlytrade.app.ui.home.products.add.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

interface AddProductColorScheme {
    val topBarBG: Color
    val screenBG: Color
    val addProductBtn: Color
}


val addProductColorScheme @Composable
get() = if(isSystemInDarkTheme()) DarkAddProductColorScheme else LightAddProductColorScheme