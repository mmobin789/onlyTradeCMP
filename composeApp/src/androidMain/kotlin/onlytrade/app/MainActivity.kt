package onlytrade.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import onlytrade.app.di.OTBusinessModule
import onlytrade.app.ui.design.components.ScreenSize
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
                    Navigator(SplashScreen(object : ScreenSize {
                        override val width: Int
                            get() = screenWidth
                        override val height: Int
                            get() = screenHeight
                    }))
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}