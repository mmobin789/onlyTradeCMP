package onlytrade.app.ui.login.kyc.colorScheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


interface KycColorScheme {
    val screenBG: Color
    val completeKycBarBG: Color
    val addDocumentsBtn: Color
    val uploadBtn: Color
}


val kycColorScheme @Composable
get() = if(isSystemInDarkTheme()) DarkKycColorScheme else LightKycColorScheme