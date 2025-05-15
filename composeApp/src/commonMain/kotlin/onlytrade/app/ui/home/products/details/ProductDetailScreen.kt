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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.W200
import androidx.compose.ui.text.font.FontWeight.Companion.W300
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import com.valentinilk.shimmer.shimmer
import onlytrade.app.ui.design.components.DotsIndicator
import onlytrade.app.ui.design.components.LocalSharedCMP
import onlytrade.app.ui.design.components.getToast
import onlytrade.app.ui.home.products.details.colorScheme.productDetailColorScheme
import onlytrade.app.ui.home.products.my.MyProductsScreen
import onlytrade.app.ui.home.trades.MyTradesScreen
import onlytrade.app.viewmodel.product.ui.ProductDetailViewModel
import onlytrade.app.viewmodel.product.ui.state.ProductDetailUiState.GuestUser
import onlytrade.app.viewmodel.product.ui.state.ProductDetailUiState.LoadingOfferMade
import onlytrade.app.viewmodel.product.ui.state.ProductDetailUiState.MakeOfferFail
import onlytrade.app.viewmodel.product.ui.state.ProductDetailUiState.MakingOffer
import onlytrade.app.viewmodel.product.ui.state.ProductDetailUiState.OfferMade
import onlytrade.app.viewmodel.product.ui.state.ProductDetailUiState.OfferNotMade
import onlytrade.app.viewmodel.product.ui.state.ProductDetailUiState.OfferReceived
import onlytrade.app.viewmodel.product.ui.state.ProductDetailUiState.OfferRejected
import onlytrade.app.viewmodel.product.ui.state.ProductDetailUiState.OfferWithdrawn
import onlytrade.app.viewmodel.product.ui.state.ProductDetailUiState.OffersExceeded
import onlytrade.app.viewmodel.product.ui.state.ProductDetailUiState.WithdrawingOffer
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_name
import onlytrade.composeapp.generated.resources.home_5
import onlytrade.composeapp.generated.resources.ok
import onlytrade.composeapp.generated.resources.productDetail_1
import onlytrade.composeapp.generated.resources.productDetail_2
import onlytrade.composeapp.generated.resources.productDetail_3
import onlytrade.composeapp.generated.resources.productDetail_4
import onlytrade.composeapp.generated.resources.productDetail_5
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random

class ProductDetailScreen(private val productId: Long, private val tradeView: Boolean = false) :
    Screen {
    @Composable
    override fun Content() {
        val product = ProductCache.get(productId)!!
        val nav = LocalNavigator.currentOrThrow
        val sharedCMP = LocalSharedCMP.current
        val viewModel = koinViewModel<ProductDetailViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val imageUrls = product.imageUrls

        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (header, back, like, dots, space, content) = createRefs()

            val pagerState = rememberPagerState { imageUrls.size }

            HorizontalPager(
                modifier = Modifier.constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, state = pagerState
            ) { page ->

                AsyncImage(
                    model = imageUrls[page],
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clipToBounds().background(
                        color = Color(
                            Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                        )
                    ).fillMaxWidth().height((sharedCMP.screenHeight / 3).dp)
                )


            }

            Icon(
                modifier = Modifier.constrainAs(back) {
                    top.linkTo(header.top)
                    start.linkTo(header.start)
                }.clickable { nav.pop() }.padding(16.dp),
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = stringResource(Res.string.ok)
            )

            Icon(
                modifier = Modifier.constrainAs(like) {
                    top.linkTo(header.top)
                    end.linkTo(header.end)
                }.padding(8.dp).background(
                    shape = CircleShape, color = MaterialTheme.colorScheme.tertiary
                ).padding(8.dp),
                imageVector = Icons.Outlined.Favorite,
                contentDescription = stringResource(Res.string.app_name)
            )

            DotsIndicator(
                modifier = Modifier.constrainAs(dots) {
                    start.linkTo(header.start)
                    end.linkTo(header.end)
                    bottom.linkTo(space.top)
                }.padding(bottom = 8.dp).padding(horizontal = 8.dp),

                totalDots = imageUrls.size,
                selectedIndex = pagerState.currentPage,
                selectedColor = Color(0xFF474567),
                unSelectedColor = Color(0xFFF4EAE9)
            )


            Spacer(
                modifier = Modifier.size(16.dp).background(
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    color = MaterialTheme.colorScheme.background
                ).constrainAs(space) {
                    bottom.linkTo(header.bottom)
                    start.linkTo(header.start)
                    end.linkTo(header.end)
                })



            ConstraintLayout(
                modifier = Modifier.fillMaxSize().background(
                    color = productDetailColorScheme.screenBG,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                ).constrainAs(content) {
                    top.linkTo(dots.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

            ) {
                val (tags, productTitle, productDescription, buttons) = createRefs()

                Row(
                    modifier = Modifier.constrainAs(tags) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)

                    }.padding(top = 16.dp).padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(times = 2) { i ->


                        Text(
                            modifier = Modifier.background(
                                color = productDetailColorScheme.productTagsBG,
                                shape = MaterialTheme.shapes.medium
                            ).padding(8.dp),
                            text = if (i == 0) "1st Hand" else "In Demand",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = W200)
                        )

                    }
                }

                Text(
                    text = product.name,
                    modifier = Modifier.constrainAs(productTitle) {
                        top.linkTo(tags.bottom)
                        start.linkTo(tags.start)
                    }.padding(16.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = W500)
                )


                Text(
                    text = product.description,
                    modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 32.dp)
                        .verticalScroll(rememberScrollState()).constrainAs(productDescription) {
                            top.linkTo(productTitle.bottom)
                            start.linkTo(productTitle.start)
                            end.linkTo(parent.end)

                        },
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = W300)
                )
                if (tradeView.not())
                    Row(
                        modifier = Modifier.constrainAs(buttons) {
                            top.linkTo(productDescription.bottom)
                            start.linkTo(productDescription.start)
                            end.linkTo(productDescription.end)
                        }.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // val noOfferOnProduct by remember(product.offers) { mutableStateOf(product.offers.isNullOrEmpty()) }

                        LaunchedEffect(Unit) {
                            viewModel.checkOffer(product)
                        }

                        val madeOffer by remember(product.offers, uiState) {
                            mutableStateOf(product.offers?.find {
                                viewModel.madeOffer(
                                    it.offerMakerId
                                )
                            } != null || uiState is OfferMade) // client persists the offer if placed.
                        }


                        val receivedOffer by remember(product.offers, uiState) {
                            mutableStateOf(product.offers?.find { viewModel.receivedOffer(it.offerReceiverId) } != null || uiState == OfferReceived)
                        }

                        val offerTrade =
                            uiState == GuestUser || uiState == LoadingOfferMade || uiState == MakingOffer || uiState == MakeOfferFail || uiState == OfferNotMade || uiState == OfferWithdrawn

                        if (madeOffer || uiState == WithdrawingOffer) OutlinedButton(
                            modifier = if (uiState == WithdrawingOffer) Modifier.weight(1f)
                                .shimmer() else Modifier.weight(1f),
                            onClick = {
                                when (uiState) {
                                    WithdrawingOffer -> {
                                        getToast().showToast("Withdrawing trade please wait")

                                    }

                                    OfferWithdrawn -> { // offer withdrawn click disabled.
                                        getToast().showToast("Trade withdrawn. please await refresh.")
                                        viewModel.idle()
                                    }

                                    OfferRejected -> { // offer rejected by receiver click disabled.
                                        getToast().showToast("Trade rejected. please await refresh.")
                                        viewModel.idle()
                                    }

                                    else -> viewModel.withdrawOffer(product.id)
                                }
                            },
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(
                                1.dp, productDetailColorScheme.offerTradeBtnBorder
                            ),
                        ) {
                            Text(
                                text = stringResource(if (uiState == WithdrawingOffer) Res.string.productDetail_5 else Res.string.productDetail_3),
                                color = productDetailColorScheme.offerTradeBtnText,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                        else if (offerTrade) Button(
                            modifier = if (uiState == LoadingOfferMade || uiState is MakingOffer) Modifier.weight(
                                1f
                            ).shimmer() else Modifier.weight(1f),
                            onClick = {
                                when (uiState) {
                                    GuestUser -> {
                                        //todo display login screen.
                                        getToast().showToast("Please login first.")

                                    }

                                    OffersExceeded -> getToast().showToast("This product has too many offers please wait.")
                                    is MakingOffer -> getToast().showToast("Please wait for offer to be made.")
                                    else -> nav.push(MyProductsScreen { pickedProductIds ->
                                        viewModel.makeOffer(
                                            productId = product.id,
                                            offerReceiverId = product.userId,
                                            offeredProductIds = pickedProductIds
                                        )


                                    })
                                }
                            },
                            shape = MaterialTheme.shapes.medium,
                            colors = ButtonDefaults.buttonColors(productDetailColorScheme.buyProductBtn)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = stringResource(
                                        if (uiState == LoadingOfferMade) Res.string.home_5 else if (uiState is MakingOffer) Res.string.productDetail_2 else Res.string.productDetail_1
                                    ), modifier = Modifier.padding(vertical = 8.dp)
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                        else if (receivedOffer) Button(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                nav.push(MyTradesScreen())
                            },
                            shape = MaterialTheme.shapes.medium,
                            colors = ButtonDefaults.buttonColors(productDetailColorScheme.buyProductBtn)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = stringResource(Res.string.productDetail_4),
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