package onlytrade.app.ui.design.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

interface SharedCMP {
    val screenWidth: Dp
    val screenHeight: Dp

    @Composable
    fun GetImagesFromGallery(onImagesPicked: (List<ByteArray>) -> Unit)
}