package onlytrade.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import onlytrade.app.di.OTBusinessModule
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
        setContent { KoinContext { AppTheme() { Navigator(SplashScreen()) } } }
    }
}
