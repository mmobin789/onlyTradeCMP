package onlytrade.app.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import onlytrade.app.android.R
import onlytrade.app.ui.design_system.theme.onlyTradePrimary

@Composable
fun OBPage1(onNextClick: () -> Unit) {
    Scaffold(bottomBar = {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .padding(bottom = 44.dp),
            onClick = onNextClick,
            shape = MaterialTheme.shapes.small,
        ) {
            Text("Next", modifier = Modifier)
        }
    }, topBar = {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .background(
                    MaterialTheme.colorScheme.onlyTradePrimary, shape = MaterialTheme.shapes.large
                )
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    model = R.drawable.ic_quickmart_intro,
                    contentScale = ContentScale.None,
                    contentDescription = stringResource(R.string.app_logo)
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
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    style = MaterialTheme.typography.headlineSmall.copy(textAlign = TextAlign.Center),
                    text = "Explore a wide range of products",
                )
                Text(
                    text = "Explore a wide range of products at your\nfingertips.QuickMart offers an extensive\ncollection to suit your needs.",
                    style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
                )
            }
        }
    }
}
