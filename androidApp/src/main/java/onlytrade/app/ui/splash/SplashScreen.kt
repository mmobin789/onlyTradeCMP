package onlytrade.app.ui.splash

import androidx.compose.foundation.background
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import onlytrade.app.android.R
import onlytrade.app.ui.onboarding.OBScrollPage
import onlytrade.app.ui.onboarding.drawableRes


class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = drawableRes(R.drawable.quickmart_light, R.drawable.quickmart_dark),
                contentScale = ContentScale.None,
                contentDescription = stringResource(R.string.app_logo)
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(R.string.app_desc),
                fontWeight = FontWeight.SemiBold
            )

        }

        LaunchedEffect(true) {
            delay(500)
            nav.replace(OBScrollPage())

        }

    }
}
