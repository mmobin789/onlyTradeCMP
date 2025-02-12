package onlytrade.app.ui.onboarding.obPage1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import onlytrade.app.ui.onboarding.obPage1.colorScheme.ob1ColorScheme
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_logo
import onlytrade.composeapp.generated.resources.ic_quickmart
import onlytrade.composeapp.generated.resources.ic_quickmart_dark
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun OBPage1(onNextClick: () -> Unit) {
    Scaffold(
        topBar = {
        Row(
            modifier = Modifier.background(ob1ColorScheme.topBarBG)
                .padding(16.dp)
                .background(
                    color = ob1ColorScheme.obLogoBar, shape = MaterialTheme.shapes.large
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
                    modifier = Modifier.clickable {
                        onNextClick()
                    })

            }

    }) { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(ob1ColorScheme.screenBG)
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
                    text = "Explore a wide range of products",
                )
                Text(
                    text = "Explore a wide range of products at your\nfingertips.QuickMart offers an extensive\ncollection to suit your needs.",
                    style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
                )
                Button(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp),
                    onClick = onNextClick,
                    colors = ButtonDefaults.buttonColors(ob1ColorScheme.nextBtn),

                ) {
                    Text(text = "Next", color = Color.White, modifier = Modifier)
                }
            }
        }
    }
}
