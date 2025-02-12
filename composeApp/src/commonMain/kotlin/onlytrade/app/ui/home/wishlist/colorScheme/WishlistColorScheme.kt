package onlytrade.app.ui.home.wishlist.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color



interface WishlistColorScheme {
    val screenBG: Color
    val botBarBG: Color
    val wishlistBarBG: Color
}


val wishlistColorScheme @Composable
get() = if(isSystemInDarkTheme()) DarkWishlistColorScheme else LightWishlistColorScheme