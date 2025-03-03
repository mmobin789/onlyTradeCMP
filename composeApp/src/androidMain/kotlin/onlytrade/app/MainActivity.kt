package onlytrade.app

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import onlytrade.app.di.OTBusinessModule
import onlytrade.app.ui.design.components.SharedCMP
import onlytrade.app.ui.design.theme.AppTheme
import onlytrade.app.ui.splash.SplashScreen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinContext

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
                        override fun GetImageFromGallery(onImagePicked: (ByteArray) -> Unit) {
                            LoadImageFromGallery(onImagePicked)
                        }
                    }))
                }
            }
        }
    }
}

@Composable
private fun LoadImageFromGallery(onImagePicked: (ByteArray) -> Unit) {
    val context = LocalContext.current
    rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.run {
            try {

                context.contentResolver.openInputStream(this)?.also {
                    onImagePicked(
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            it.readAllBytes()
                        } else it.readBytes()
                    )
                    it.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}