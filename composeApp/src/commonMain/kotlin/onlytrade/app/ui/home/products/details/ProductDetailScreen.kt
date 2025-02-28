package onlytrade.app.ui.home.products.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W200
import androidx.compose.ui.text.font.FontWeight.Companion.W300
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import onlytrade.app.ui.design.components.DotsIndicator
import onlytrade.app.ui.design.components.ScreenSize
import onlytrade.app.ui.home.products.details.colorScheme.productDetailColorScheme
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_name
import onlytrade.composeapp.generated.resources.ok
import org.jetbrains.compose.resources.stringResource
import kotlin.random.Random

class ProductDetailScreen(private val id: Int, private val screenSize: ScreenSize) : Screen {

    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        // var headerVisible by remember { mutableStateOf(true) }
        // var headerVisible = true

        ConstraintLayout(modifier = Modifier.fillMaxSize().background(color = Color.Green)) {
            val (header, back, like, dots, space, content) = createRefs()

            //    AnimatedVisibility(visible = headerVisible) {

            val pagerState = rememberPagerState { 5 }

            HorizontalPager(
                modifier = Modifier.constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, state = pagerState
            ) { page ->

                Spacer(
                    modifier = Modifier
                        .background(
                            color = Color(
                                Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                            )
                        )
                        .fillMaxWidth()
                        .height((screenSize.height / 3).dp)
                )


            }

            Icon(modifier = Modifier
                .constrainAs(back) {
                    top.linkTo(header.top)
                    start.linkTo(header.start)
                }
                .clickable { nav.pop() }
                .padding(16.dp),
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = stringResource(Res.string.ok))

            Icon(modifier = Modifier
                .constrainAs(like) {
                    top.linkTo(header.top)
                    end.linkTo(header.end)
                }
                .padding(8.dp)
                .background(
                    shape = CircleShape, color = MaterialTheme.colorScheme.tertiary
                )
                .padding(8.dp),
                imageVector = Icons.Outlined.Favorite,
                contentDescription = stringResource(Res.string.app_name))

            DotsIndicator(
                modifier = Modifier
                    .constrainAs(dots) {
                        start.linkTo(header.start)
                        end.linkTo(header.end)
                        bottom.linkTo(space.top)
                    }
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 8.dp),

                totalDots = 5,
                selectedIndex = pagerState.currentPage,
                selectedColor = Color(0xFF474567),
                unSelectedColor = Color(0xFFF4EAE9)
            )


            Spacer(
                modifier = Modifier
                    .size(16.dp)
                    .background(
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                        color = MaterialTheme.colorScheme.background
                    )
                    .constrainAs(space) {
                        bottom.linkTo(header.bottom)
                        start.linkTo(header.start)
                        end.linkTo(header.end)
                    }
            )



            ConstraintLayout(modifier = Modifier
                .fillMaxSize()
                .background(
                    color = productDetailColorScheme.screenBG,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
                .constrainAs(content) {
                    top.linkTo(dots.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

            ) {
                val (tags, productTitle, productDescription, buttons) = createRefs()

                Row(
                    modifier = Modifier
                        .constrainAs(tags) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)

                        }
                        .padding(top = 16.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(times = 2) { i ->


                        Text(
                            modifier = Modifier
                                .background(
                                    color = productDetailColorScheme.productTagsBG,
                                    shape = MaterialTheme.shapes.medium
                                )
                                .padding(8.dp),
                            text = if (i == 0) "1st Hand" else "In Demand",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = W200)
                        )

                    }
                }

                Text(
                    text = "Product ${id + 1}",
                    modifier = Modifier
                        .constrainAs(productTitle) {
                            top.linkTo(tags.bottom)
                            start.linkTo(tags.start)
                        }
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = W500)
                )


                Text(
                    text = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 32.dp)
                        .verticalScroll(rememberScrollState())
                        .constrainAs(productDescription) {
                            top.linkTo(productTitle.bottom)
                            start.linkTo(productTitle.start)
                            end.linkTo(parent.end)

                        },
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = W300)
                )

                Row(
                    modifier = Modifier
                        .constrainAs(buttons) {
                            top.linkTo(productDescription.bottom)
                            start.linkTo(productDescription.start)
                            end.linkTo(productDescription.end)
                        }
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = { },
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(1.dp, productDetailColorScheme.offerTradeBtnBorder),
                    ) {
                        Text(
                            text = "Offer Trade", color = productDetailColorScheme.offerTradeBtnText, modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {},
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(productDetailColorScheme.buyProductBtn)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Buy Product",
                                modifier = Modifier.padding(vertical = 8.dp)
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
    }

}