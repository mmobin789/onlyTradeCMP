package onlytrade.app.ui.home.products

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W200
import androidx.compose.ui.text.font.FontWeight.Companion.W300
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import onlytrade.app.android.R
import onlytrade.app.ui.home.products.details.ProductDetailScreen
import kotlin.random.Random

class ProductsScreen(private val categoryName: String? = null) : Screen {

    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val productGridState = rememberLazyGridState()
        val headerVisible = productGridState.canScrollBackward.not()

        Scaffold(topBar = {
            AnimatedVisibility(visible = headerVisible) {
                Column {
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {


                        Icon(
                            modifier = Modifier.clickable { nav.pop() },
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = stringResource(android.R.string.cancel)
                        )

                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = categoryName ?: "Latest Products",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = W700)
                        )


                    }

                    Spacer(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.tertiary)
                            .height(1.dp)
                            .fillMaxWidth()
                    )

                }
            }
        }, bottomBar = {
            Row(
                modifier = Modifier.padding(8.dp),
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
                        contentDescription = stringResource(R.string.app_name)
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
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = stringResource(R.string.app_name)
                    )

                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "TradeCart",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = W200)
                    )
                }
                Column(Modifier.weight(1f)) {

                    Icon(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = stringResource(R.string.app_name)
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
                        contentDescription = stringResource(R.string.app_name)
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Profile",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = W200)
                    )
                }
            }

        }) { paddingValues ->

            LazyVerticalGrid(
                state = productGridState,
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                columns = GridCells.Fixed(2)
            ) {

                items(10) { i ->
                    ProductUI(i)
                }
            }
        }


    }

    @Composable
    private fun ProductUI(index: Int) {
        val size = (LocalConfiguration.current.screenWidthDp / 2).dp
        val nav = LocalNavigator.currentOrThrow
        Column(modifier = Modifier.clickable {
            nav.push(ProductDetailScreen(index))
        }) {
            Box(
                Modifier
                    .size(size)
                    .background(
                        color = Color(
                            Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                        ), shape = MaterialTheme.shapes.extraLarge
                    )
            ) {

                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            shape = CircleShape, color = MaterialTheme.colorScheme.onSecondary
                        )
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = stringResource(android.R.string.search_go)
                )
            }

            ConstraintLayout(
                modifier = Modifier.padding(vertical = 16.dp)
            ) {

                val (c1, c2, c3, s1, s2, colorsTxt, productName, price, discountPrice) = createRefs()

                Spacer(modifier = Modifier
                    .constrainAs(c1) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .size(24.dp)
                    .background(
                        shape = CircleShape, color = Color(
                            Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                        )
                    ))

                Spacer(modifier = Modifier
                    .width(12.dp)
                    .constrainAs(s1) {
                        top.linkTo(c1.top)
                        bottom.linkTo(c1.bottom)
                        start.linkTo(c1.start)
                        end.linkTo(c1.end)

                    })

                Spacer(modifier = Modifier
                    .constrainAs(c2) {
                        start.linkTo(s1.end)
                        top.linkTo(parent.top)
                    }
                    .size(24.dp)
                    .background(
                        shape = CircleShape, color = Color(
                            Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                        )
                    ))

                Spacer(modifier = Modifier
                    .width(12.dp)
                    .constrainAs(s2) {
                        top.linkTo(c2.top)
                        bottom.linkTo(c2.bottom)
                        start.linkTo(c2.start)
                        end.linkTo(c2.end)

                    })
                Spacer(modifier = Modifier
                    .constrainAs(c3) {
                        start.linkTo(s2.end)
                        top.linkTo(parent.top)
                    }
                    .size(24.dp)
                    .background(
                        shape = CircleShape, color = Color(
                            Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                        )
                    ))

                Text(modifier = Modifier
                    .constrainAs(colorsTxt) {
                        top.linkTo(parent.top)
                        start.linkTo(c3.end)

                    }
                    .padding(horizontal = 16.dp),
                    textDecoration = TextDecoration.Underline,
                    text = "All ${Random.nextInt(2, 10)} colors",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = W300))

                Text(modifier = Modifier
                    .constrainAs(productName) {
                        top.linkTo(c1.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(top = 16.dp),
                    text = "Product $index",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = W500))

                Text(
                    modifier = Modifier.constrainAs(price) {
                        top.linkTo(productName.bottom)
                        start.linkTo(productName.start)
                    },
                    text = "$${Random.nextInt(index, 500)}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = W500)
                )

                Text(
                    modifier = Modifier.constrainAs(discountPrice) {
                        top.linkTo(price.bottom)
                        start.linkTo(price.start)

                    },
                    textDecoration = TextDecoration.LineThrough,
                    text = "$${Random.nextInt(index, 500)}",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = W300)
                )
            }
        }
    }
}