package onlytrade.app.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import onlytrade.app.ui.design.components.PrimaryButton
import onlytrade.app.ui.design.theme.onlyTradePrimary
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_logo
import onlytrade.composeapp.generated.resources.ic_quickmart
import onlytrade.composeapp.generated.resources.ic_quickmart_dark
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun OBPage2(onNextClick: () -> Unit) {
    Scaffold(bottomBar = {
        PrimaryButton(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .padding(bottom = 56.dp)
                .fillMaxWidth(),
            onClick = onNextClick,
            text = "Next"
        )
    }, topBar = {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .background(
                    onlyTradePrimary, shape = MaterialTheme.shapes.large
                )
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.width(100.dp).height(30.dp),
                imageVector = vectorResource(if (isSystemInDarkTheme()) Res.drawable.ic_quickmart_dark else Res.drawable.ic_quickmart),
                contentScale = ContentScale.FillBounds,
                contentDescription = stringResource(Res.string.app_logo)
            )

            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = "Skip for now",

                )
        }
    }) { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    style = MaterialTheme.typography.headlineSmall.copy(textAlign = TextAlign.Center),
                    text = "Unlock exclusive offers and discounts",
                )
                Text(
                    text = "Get access to limited-time deals and special\n promotions available only to our valued\n customers.",
                    style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
                )
            }
        }
    }
}
