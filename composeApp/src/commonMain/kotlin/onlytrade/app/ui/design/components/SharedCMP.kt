package onlytrade.app.ui.design.components

import androidx.compose.runtime.Composable

interface SharedCMP {
    val screenWidth: Int
    val screenHeight: Int

    @Composable
    fun GetImagesFromGallery(onImagesPicked: (List<ByteArray>) -> Unit)
}