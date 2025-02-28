package onlytrade.app.ui.home.categories.sub.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


interface SubCategoriesColorScheme {
    val screenBG: Color
    val botBarBG: Color
    val categoryBarBG: Color
}


val subCategoriesColorScheme @Composable
get() = if(isSystemInDarkTheme()) DarkSubCategoriesColorScheme else LightSubCategoriesColorScheme