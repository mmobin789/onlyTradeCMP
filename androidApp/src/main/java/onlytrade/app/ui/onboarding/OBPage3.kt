package onlytrade.app.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import onlytrade.app.android.R


@Composable
fun OBPage3(onLoginClick: () -> Unit, onGetStartedClick: () -> Unit) {

    Box(
        Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp)
    ) {

        Column {

            Spacer(modifier = Modifier.padding(top = 16.dp))
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(
                        color = color(0xFFF4FDFA, 0xFF212322),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.align(Alignment.CenterStart),
                    model = drawableRes(
                        R.drawable.quickmart_light_intro,
                        R.drawable.quickmart_dark_intro
                    ),
                    contentScale = ContentScale.None,
                    contentDescription = stringResource(R.string.app_logo)
                )

                Text(
                    color = Color(0xFF21D4B4),
                    text = "Skip for now",
                    modifier = Modifier.align(Alignment.CenterEnd)
                )

            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp),
                fontSize = 24.sp,
                color = color(
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.tertiary
                ),
                text = "Safe and Secure\nDeals",

                )
            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = color(0xFF6F7384, 0xFFA2A2A6),
                text = " OnlyTrade provides full scale arbitration\nand customer support for your\ntrusted trades and offers."
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), onClick = {


                }, shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = color(
                        MaterialTheme.colorScheme.secondary,
                        Color(0xFF21D4B4)
                    )
                )
            ) {
                Text("Next", color = Color.White, modifier = Modifier.padding(vertical = 8.dp))
            }

        }

    }

}