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
    private fun LoadImageFromGallery(onImagesPicked: (List<ByteArray>) -> Unit) {
        val scope = rememberCoroutineScope()
        val imagePicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents()
        ) { uris: List<Uri> ->
            scope.launch {
                //todo this will cause OOM need to optimize.
                val images = withContext(Dispatchers.IO) { uris.mapNotNull { uriToFullSizeByteArray(it) } }
                withContext(Dispatchers.Main.immediate) { onImagesPicked(images) }
            }
        }
        LaunchedEffect(Unit) {
            imagePicker.launch("image/*")
        }


    }

    private fun uriToFullSizeByteArray(uri: Uri): ByteArray? {
        return try {
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val bitmap = BitmapFactory.decodeStream(inputStream) // Decode full image
            inputStream.close()

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream) // Preserve quality
            val byteArray = outputStream.toByteArray() // Convert to ByteArray
            outputStream.close()
            byteArray
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}