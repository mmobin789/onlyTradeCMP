package onlytrade.app.ui.home.products.details

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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import onlytrade.app.ui.design.components.DotsIndicator
import onlytrade.app.ui.home.categories.sub.SubCategoriesScreen
import onlytrade.app.ui.home.products.ProductsScreen
import kotlin.random.Random

class ProductDetailScreen(id: Int) : Screen {

    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        // var headerVisible by remember { mutableStateOf(true) }
        var headerVisible = true

        Scaffold(
            topBar = {
                Column {
                    AnimatedVisibility(visible = headerVisible) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {

                            val pagerState = rememberPagerState { 5 }

                            HorizontalPager(
                                state = pagerState
                            ) { page ->

                                Spacer(
                                    modifier = Modifier
                                        .background(
                                            color = Color(
                                                Random.nextFloat(),
                                                Random.nextFloat(),
                                                Random.nextFloat()
                                            )
                                        )
                                        .fillMaxWidth()
                                        .height((LocalConfiguration.current.screenHeightDp / 3).dp)
                                )


                            }

                            Icon(
                                modifier = Modifier
                                    .clickable { nav.pop() }
                                    .align(Alignment.TopStart)
                                    .padding(16.dp),
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = stringResource(android.R.string.ok)
                            )

                            Icon(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.tertiary
                                    )
                                    .padding(8.dp)
                                    .align(Alignment.TopEnd),
                                imageVector = Icons.Outlined.Favorite,
                                contentDescription = stringResource(R.string.app_name)
                            )
                            DotsIndicator(
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                                    .padding(horizontal = 8.dp)
                                    .align(Alignment.BottomCenter),
                                totalDots = 5,
                                selectedIndex = pagerState.currentPage,
                                selectedColor = MaterialTheme.colorScheme.tertiary,
                                unSelectedColor =MaterialTheme.colorScheme.secondary
                            )

                        }
                    }
                }
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .padding(bottom = 56.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .weight(1f), onClick = { }, shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = "Offer Trade",
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    Button(
                        modifier = Modifier
                            .weight(1f),
                        onClick = {},
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "View Trade Requests",
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
            })
        { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                        color = MaterialTheme.colorScheme.background
                    )
                    .padding(paddingValues)
                    .padding(horizontal = 32.dp)

            ) {

                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .background(
                                color = Color(
                                    Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                                ), shape = MaterialTheme.shapes.medium
                            )
                            .padding(8.dp),
                        text = "1st Hand",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = W200)
                    )
                    Text(
                        modifier = Modifier
                            .background(
                                color = Color(
                                    Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                                ), shape = MaterialTheme.shapes.medium
                            )
                            .padding(8.dp),
                        text = "In Demand",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = W200)
                    )
                }

            }

        }
    }
}

@Composable
private fun ProductUI(index: Int) {
    val size = (LocalConfiguration.current.screenWidthDp / 2).dp

    Column {
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

            Spacer(
                modifier = Modifier
                    .constrainAs(c1) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .size(24.dp)
                    .background(
                        shape = CircleShape, color = Color(
                            Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                        )
                    )
            )

            Spacer(modifier = Modifier
                .width(12.dp)
                .constrainAs(s1) {
                    top.linkTo(c1.top)
                    bottom.linkTo(c1.bottom)
                    start.linkTo(c1.start)
                    end.linkTo(c1.end)

                })

            Spacer(
                modifier = Modifier
                    .constrainAs(c2) {
                        start.linkTo(s1.end)
                        top.linkTo(parent.top)
                    }
                    .size(24.dp)
                    .background(
                        shape = CircleShape, color = Color(
                            Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                        )
                    )
            )

            Spacer(modifier = Modifier
                .width(12.dp)
                .constrainAs(s2) {
                    top.linkTo(c2.top)
                    bottom.linkTo(c2.bottom)
                    start.linkTo(c2.start)
                    end.linkTo(c2.end)

                })
            Spacer(
                modifier = Modifier
                    .constrainAs(c3) {
                        start.linkTo(s2.end)
                        top.linkTo(parent.top)
                    }
                    .size(24.dp)
                    .background(
                        shape = CircleShape, color = Color(
                            Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                        )
                    )
            )

            Text(
                modifier = Modifier
                    .constrainAs(colorsTxt) {
                        top.linkTo(parent.top)
                        start.linkTo(c3.end)

                    }
                    .padding(horizontal = 16.dp),
                textDecoration = TextDecoration.Underline,
                text = "All ${Random.nextInt(2, 10)} colors",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = W300)
            )

            Text(
                modifier = Modifier
                    .constrainAs(productName) {
                        top.linkTo(c1.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(top = 16.dp),
                text = "Product $index",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = W500)
            )

            Text(
                modifier = Modifier
                    .constrainAs(price) {
                        top.linkTo(productName.bottom)
                        start.linkTo(productName.start)
                    },
                text = "$${Random.nextInt(index, 500)}",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = W500)
            )

            Text(
                modifier = Modifier
                    .constrainAs(discountPrice) {
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