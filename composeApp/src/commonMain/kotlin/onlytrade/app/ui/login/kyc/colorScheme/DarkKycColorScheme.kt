package onlytrade.app.ui.login.kyc.colorScheme

import androidx.compose.ui.graphics.Color


object DarkKycColorScheme: KycColorScheme {
    override val screenBG: Color
        get() = Color(0xFF101D25)
    override val completeKycBarBG: Color
        get() = Color(0xFF232D36)
    override val addDocumentsBtn: Color
        get() = Color(0xFFD7F0D8)
    override val uploadBtn: Color
        get() = Color(0xFFD7F0D8)
}