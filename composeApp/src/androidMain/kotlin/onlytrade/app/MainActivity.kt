package onlytrade.app

import DatabaseDriverFactory
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import onlytrade.app.di.OTBusinessModule
import onlytrade.app.ui.design.components.SharedCMP
import onlytrade.app.ui.design.components.getAsyncImageLoader
import onlytrade.app.ui.design.theme.AppTheme
import onlytrade.app.ui.splash.SplashScreen
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinContext
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        OTBusinessModule.run(
            platformInit = { androidContext(this@MainActivity) },
            databaseDriverFactory = DatabaseDriverFactory(this)
        )



        setContent {
            val localConfig = LocalConfiguration.current
            val screenWidth = localConfig.screenWidthDp
            val screenHeight = localConfig.screenHeightDp
            KoinContext {
                AppTheme {
                    Navigator(SplashScreen(object : SharedCMP {
                        override val screenWidth: Int
                            get() = screenWidth
                        override val screenHeight: Int
                            get() = screenHeight

                        @Composable
                        override fun GetImagesFromGallery(onImagesPicked: (List<ByteArray>) -> Unit) {
                            LoadImageFromGallery(onImagesPicked)
                        }
                    }))
                }
            }
        }
    }


    @Composable
    private fun LoadImageFromGallery(
        onImagesPicked: (List<ByteArray>) -> Unit,
    ) {
        val scope = rememberCoroutineScope()
        val imagePicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents()
        ) { uris: List<Uri> ->


            scope.launch {


                val images =
                    withContext(Dispatchers.IO) {
                        uris.mapNotNull {
                            loadImageAsByteArray(it)
                        }
                    }
                withContext(Dispatchers.Main.immediate) { onImagesPicked(images) }
            }
        }
        LaunchedEffect(Unit) {
            imagePicker.launch("image/*")
        }

    }


    private suspend fun loadImageAsByteArray(uri: Uri): ByteArray? {
        val imageLoader = getAsyncImageLoader(this)
        val request = ImageRequest.Builder(this)
            .size(1600)
            .data(uri)
            .build()

        return when (val result = imageLoader.execute(request)) {
            is SuccessResult -> {
                result.image.toBitmap().let { bitmap ->
                    //  val resizedBitmap = bitmap.resize(1600)
                    //   val byteArray = resizedBitmap.toByteArray(70)
                    val byteArray = bitmap.toByteArray(70)
                    Log.d(
                        "ImagePicker",
                        "Resized size: ${bitmap.allocationByteCount / 1024} KB | " +
                                "Compressed size: ${byteArray.size / 1024} KB"
                    )
                    bitmap.recycle() // Free memory
                    // resizedBitmap.recycle() // Free memory
                    byteArray
                }
            }

            else -> null
        }
    }

    // ✅ Resize Bitmap to a Max Size (Maintains Aspect Ratio)
    /*
        private fun Bitmap.resize(maxSize: Int): Bitmap {
            val aspectRatio = width.toFloat() / height.toFloat()
            val newWidth = if (width > height) maxSize else (maxSize * aspectRatio).toInt()
            val newHeight = if (height > width) maxSize else (maxSize / aspectRatio).toInt()
            return Bitmap.createScaledBitmap(this, newWidth, newHeight, true)
        }
    */

    // ✅ Convert Bitmap to ByteArray
    private fun Bitmap.toByteArray(quality: Int): ByteArray {
        return ByteArrayOutputStream().use { outputStream ->
            this.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.toByteArray()
        }
    }


}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}