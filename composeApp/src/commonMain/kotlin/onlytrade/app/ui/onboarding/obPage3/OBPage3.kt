package onlytrade.app.ui.onboarding.obPage3

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import onlytrade.app.ui.onboarding.obPage3.colorScheme.ob3ColorScheme
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_logo
import onlytrade.composeapp.generated.resources.ic_quickmart
import onlytrade.composeapp.generated.resources.ic_quickmart_dark
import onlytrade.composeapp.generated.resources.ob3_1
import onlytrade.composeapp.generated.resources.ob3_2
import onlytrade.composeapp.generated.resources.ob3_4
import onlytrade.composeapp.generated.resources.ob3_5
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun OBPage3(onLoginClick: () -> Unit, onGetStartedClick: () -> Unit) {
    Scaffold(bottomBar = {
        Row(
            modifier = Modifier.background(ob3ColorScheme.botBarBG)
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(bottom = 56.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f), onClick = onLoginClick,
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, ob3ColorScheme.loginBtnBorder),
            ) {
                Text(
                    stringResource(Res.string.ob3_4),
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = ob3ColorScheme.loginBtnText
                )
            }

            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = onGetStartedClick,
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(ob3ColorScheme.getStartedBtn)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        stringResource(Res.string.ob3_5),
                        modifier = Modifier.padding(8.dp)
                    )
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

        }
    }, topBar = {
        Row(
            modifier = Modifier.background(ob3ColorScheme.topBarBG)
                .padding(16.dp)
                .background(
                    color = ob3ColorScheme.obLogoBar, shape = MaterialTheme.shapes.large
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
                text = stringResource(Res.string.ob3_1),

                )
        }
    }) { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(ob3ColorScheme.screenBG)
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
                    text = stringResource(Res.string.ob3_2),
                )
                Text(
                    text = " OnlyTrade provides full scale arbitration\nand customer support for your\ntrusted trades and offers.",
                    style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
                )
            }
        }
    }
}
