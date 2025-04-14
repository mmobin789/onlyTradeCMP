package onlytrade.app.ui.home.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W200
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import onlytrade.app.ui.design.components.SharedCMP
import onlytrade.app.ui.home.HomeScreen
import onlytrade.app.ui.home.products.details.colorScheme.productDetailColorScheme
import onlytrade.app.ui.home.profile.colorScheme.profileColorScheme
import onlytrade.app.ui.home.wishlist.WishListScreen
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_name
import onlytrade.composeapp.generated.resources.botBar_1
import onlytrade.composeapp.generated.resources.botBar_2
import onlytrade.composeapp.generated.resources.botBar_3
import onlytrade.composeapp.generated.resources.botBar_4
import onlytrade.composeapp.generated.resources.cancel
import onlytrade.composeapp.generated.resources.outline_compare_arrows_24
import onlytrade.composeapp.generated.resources.profile_1
import onlytrade.composeapp.generated.resources.profile_2
import onlytrade.composeapp.generated.resources.profile_3
import onlytrade.composeapp.generated.resources.profile_4
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

class ProfileScreen(private val sharedCMP: SharedCMP) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(Res.string.profile_1)) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = profileColorScheme.topBarBG
                    ),
                    navigationIcon = {
                        IconButton(onClick = { nav.pop() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = stringResource(Res.string.cancel)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Profile"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .background(profileColorScheme.botBarBG)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        Modifier
                            .weight(1f)
                            .clickable { nav.push(HomeScreen(sharedCMP)) })
                    {
                        Icon(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            imageVector = Icons.Outlined.Home,
                            contentDescription = stringResource(Res.string.app_name)
                        )

                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = stringResource(Res.string.botBar_1),
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = W200)
                        )
                    }
                    Column(Modifier.weight(1f)) {
                        Icon(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            imageVector = vectorResource(Res.drawable.outline_compare_arrows_24),
                            contentDescription = stringResource(Res.string.app_name)
                        )

                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = stringResource(Res.string.botBar_2),
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = W200)
                        )
                    }

                    Column(
                        Modifier
                            .weight(1f)
                            .clickable { nav.push(WishListScreen(sharedCMP)) }) {
                        Icon(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            imageVector = Icons.Outlined.Favorite,
                            contentDescription = stringResource(Res.string.app_name)
                        )

                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = stringResource(Res.string.botBar_3),
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = W200)
                        )
                    }

                    Column(Modifier.weight(1f)) {
                        Icon(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            imageVector = Icons.Outlined.Person,
                            contentDescription = stringResource(Res.string.app_name)
                        )
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = stringResource(Res.string.botBar_4),
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = W200)
                        )
                    }
                }

            }) { paddingValues ->
            Column(
                modifier = Modifier
                    .background(profileColorScheme.screenBG)
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
            ) {
                Image(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.Gray, shape = CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "John Cena", style = MaterialTheme.typography.headlineSmall)
                Text(text = "john.cena@example.com", style = MaterialTheme.typography.bodyMedium)
                Text(text = "090078601", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = { },
                        shape = MaterialTheme.shapes.small,
                        border = BorderStroke(1.dp, profileColorScheme.activeTradesBtn),
                    ) {
                        Text(
                            text = stringResource(Res.string.profile_2), color = productDetailColorScheme.offerTradeBtnText
                        )
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { },
                        shape = MaterialTheme.shapes.small,
                        colors = ButtonDefaults.buttonColors(profileColorScheme.myOffersBtn)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = stringResource(Res.string.profile_3),
                            )
                        }
                    }
                }

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(profileColorScheme.logoutBtn),
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.profile_4),
                        modifier = Modifier.padding(vertical = 4.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}