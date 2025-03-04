package onlytrade.app

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import onlytrade.app.di.OTBusinessModule
import onlytrade.app.ui.design.components.SharedCMP
import onlytrade.app.ui.design.theme.AppTheme
import onlytrade.app.ui.splash.SplashScreen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinContext
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        OTBusinessModule.run {
            androidContext(this@MainActivity)
            androidLogger()
        }
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
                            uriToByteArray(it)
                        }
                    }
                withContext(Dispatchers.Main.immediate) { onImagesPicked(images) }
            }
        }
        LaunchedEffect(Unit) {
            imagePicker.launch("image/*")
        }

    }


    private fun uriToByteArray(
        uri: Uri,
        compressQuality: Int = 70,
        maxWidth: Int = 1000,
        maxHeight: Int = 1000
    ): ByteArray? {
        return try {
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }


            // 1. Open input stream just to get image dimensions
            contentResolver.openInputStream(uri)?.use { stream ->
                BitmapFactory.decodeStream(stream, null, options)
            }

            // 2. Calculate optimal inSampleSize to downscale the image
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
            options.inJustDecodeBounds = false

            // 3. Decode downscaled bitmap
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
            inputStream.close()

            // 4. Convert to ByteArray after compression
            val outputStream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, outputStream)
            bitmap?.recycle() // Free memory

            val byteArray = outputStream.toByteArray()
            outputStream.close()
            byteArray
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }


}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}