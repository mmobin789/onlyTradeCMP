package onlytrade.app.ui.design.components

import androidx.compose.runtime.Composable

data class SharedCMP(
    val screenWidth: Int,
    val screenHeight: Int,
    val getImagesComposable: @Composable (onImagesPicked: (List<ByteArray>) -> Unit) -> Unit
)

