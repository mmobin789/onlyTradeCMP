package onlytrade.app.ui.design.components

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.request.crossfade
import coil3.util.DebugLogger

@Composable
fun ShowToast(text: String) = getToast().showToast(text)
fun String.isValidPrice() = (contains(".").not() && contains("-").not() && length < 8)


fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).crossfade(true).logger(DebugLogger()).build()


