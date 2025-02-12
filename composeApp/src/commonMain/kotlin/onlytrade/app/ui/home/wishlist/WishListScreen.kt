package onlytrade.app.ui.home.wishlist

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W200
import androidx.compose.ui.text.font.FontWeight.Companion.W300
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import onlytrade.app.ui.design.components.ScreenSize
import onlytrade.app.ui.home.products.details.ProductDetailScreen
import onlytrade.app.ui.home.wishlist.colorScheme.wishlistColorScheme
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_name
import onlytrade.composeapp.generated.resources.cancel
import onlytrade.composeapp.generated.resources.outline_compare_arrows_24
import onlytrade.composeapp.generated.resources.search
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import kotlin.random.Random

class WishListScreen(private val screenSize: ScreenSize) : Screen {

    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val wishListState = rememberLazyListState()
        val headerVisible = wishListState.canScrollBackward.not()

        Scaffold(topBar = {
            AnimatedVisibility(visible = headerVisible) {
                Column {
                    Row(
                        modifier = Modifier
                            .background(wishlistColorScheme.wishlistBarBG)
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {


                        Icon(
                            modifier = Modifier.clickable { nav.pop() },
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = stringResource(Res.string.cancel)
                        )

                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = "Wishlist",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = W700)
                        )


                    }

                    Spacer(
                        modifier = Modifier
                            .background(wishlistColorScheme.wishlistBarBG)
                            .height(1.dp)
                            .fillMaxWidth()
                    )

                }
            }
        }, bottomBar = {
            Row(
                modifier = Modifier
                    .background(wishlistColorScheme.botBarBG)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    Modifier
                        .weight(1f)
                        .clickable {
                            nav.pop()
                        }) {
                    Icon(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        imageVector = Icons.Outlined.Home,
                        contentDescription = stringResource(Res.string.app_name)
                    )

                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Home",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = W200)
                    )
                }/*   Column(Modifier.weight(1f)) {
                           Icon(
                               modifier = Modifier.align(Alignment.CenterHorizontally),
                               imageVector = Icons.Outlined.Menu,
                               contentDescription = stringResource(R.string.app_name)
                           )

                           Text(
                               modifier = Modifier.align(Alignment.CenterHorizontally),
                               text = "Categories",
                               style = MaterialTheme.typography.titleSmall.copy(fontWeight = W200)
                           )
                       }*/
                Column(Modifier.weight(1f)) {
                    Icon(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        imageVector = vectorResource(Res.drawable.outline_compare_arrows_24),
                        contentDescription = stringResource(Res.string.app_name)
                    )

                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "My Trades",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = W200)
                    )
                }
                Column(Modifier.weight(1f)) {

                    Icon(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = stringResource(Res.string.app_name)
                    )

                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Wishlist",
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
                        text = "Profile",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = W200)
                    )
                }
            }

        }) { paddingValues ->

            LazyColumn(
                state = wishListState,
                modifier = Modifier
                    .background(wishlistColorScheme.screenBG)
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(10) { i ->
                    ProductUI(i)
                }
            }
        }


    }


    @Composable
    private fun ProductUI(index: Int) {
        val size = (screenSize.width / 3).dp
        val nav = LocalNavigator.currentOrThrow

        Row(modifier = Modifier.clickable {
            nav.push(ProductDetailScreen(index, screenSize))
        }) {
            Spacer(
                Modifier
                    .size(size)
                    .background(
                        color = Color(
                            Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                        ), shape = MaterialTheme.shapes.extraLarge
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {


                Text(
                    modifier = Modifier
                        .padding(top = 16.dp),
                    text = "Product $index",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = W500)
                )

                Text(
                    modifier = Modifier,
                    text = "$${Random.nextInt(index, 500)}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = W500)
                )

                Text(
                    modifier = Modifier,
                    textDecoration = TextDecoration.LineThrough,
                    text = "$${Random.nextInt(index, 500)}",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = W300)
                )

                Icon(
                    modifier = Modifier.align(Alignment.End),
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = stringResource(Res.string.search)
                )
            }
        }
    }
}