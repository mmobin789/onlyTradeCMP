package onlytrade.app.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import onlytrade.app.ui.design.components.ScreenSize
import onlytrade.app.ui.onboarding.OBScrollPage
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_desc
import onlytrade.composeapp.generated.resources.app_logo
import onlytrade.composeapp.generated.resources.ic_quickmart
import onlytrade.composeapp.generated.resources.ic_quickmart_dark
import org.jetbrains.compose.resources.stringResource


class SplashScreen(private val screenSize: ScreenSize) : Screen {
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = if (isSystemInDarkTheme()) Res.drawable.ic_quickmart_dark else Res.drawable.ic_quickmart,
                contentScale = ContentScale.None,
                contentDescription = stringResource(Res.string.app_logo)
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(Res.string.app_desc),
                fontWeight = FontWeight.SemiBold
            )

        }

        LaunchedEffect(true) {
            delay(500)
            nav.replace(OBScrollPage(screenSize))

        }

    }
}
