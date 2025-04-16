package onlytrade.app.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.setSingletonImageLoaderFactory
import kotlinx.coroutines.delay
import onlytrade.app.ui.design.components.SharedCMP
import onlytrade.app.ui.design.components.getAsyncImageLoader
import onlytrade.app.ui.home.HomeScreen
import onlytrade.app.ui.onboarding.OBScrollPage
import onlytrade.app.ui.splash.colorScheme.splashColorScheme
import onlytrade.app.viewmodel.splash.SplashViewModel
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_desc
import onlytrade.composeapp.generated.resources.app_logo
import onlytrade.composeapp.generated.resources.ic_quickmart
import onlytrade.composeapp.generated.resources.ic_quickmart_dark
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


class SplashScreen(private val sharedCMP: SharedCMP) : Screen {
    @Composable
    @Preview
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<SplashViewModel>()
        val isUserLoggedIn = viewModel.isUserLoggedIn()

        setSingletonImageLoaderFactory { context ->
            getAsyncImageLoader(context)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(splashColorScheme.screenBG),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = vectorResource(if (isSystemInDarkTheme()) Res.drawable.ic_quickmart_dark else Res.drawable.ic_quickmart),
                contentScale = ContentScale.None,
                contentDescription = stringResource(Res.string.app_logo)
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(Res.string.app_desc),
                fontWeight = FontWeight.SemiBold,
                color = splashColorScheme.appDesc
            )

        }

        LaunchedEffect(true) {
            delay(250)
            nav.replace(
                if (isUserLoggedIn)
                    HomeScreen(sharedCMP)
                else
                    OBScrollPage(sharedCMP)
            )

        }

    }
}
