package onlytrade.app.ui.home.products.add.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

interface AddProductColorScheme {
    val topBarBG: Color
    val bottomBarBG : Color
    val screenBG: Color
    val submitProductBtn: Color
    val lazyVerticalGridBorder: Color
}

val addProductColorScheme @Composable
get() = if(isSystemInDarkTheme()) DarkAddProductColorScheme else LightAddProductColorScheme