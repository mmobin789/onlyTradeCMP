package onlytrade.app.ui.home.trades.detail

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.W300
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextDecoration
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
import onlytrade.app.ui.design.components.SharedCMP
import onlytrade.app.ui.design.components.getToast
import onlytrade.app.ui.home.products.details.ProductDetailScreen
import onlytrade.app.ui.home.products.details.colorScheme.productDetailColorScheme
import onlytrade.app.viewmodel.product.offer.repository.data.db.Offer
import onlytrade.app.viewmodel.product.repository.data.db.Product
import onlytrade.app.viewmodel.trades.ui.TradeDetailViewModel
import onlytrade.app.viewmodel.trades.ui.state.TradeDetailUiState.AcceptingOffer
import onlytrade.app.viewmodel.trades.ui.state.TradeDetailUiState.CompletingOffer
import onlytrade.app.viewmodel.trades.ui.state.TradeDetailUiState.OfferAccepted
import onlytrade.app.viewmodel.trades.ui.state.TradeDetailUiState.OfferCompleteApiError
import onlytrade.app.viewmodel.trades.ui.state.TradeDetailUiState.OfferCompleted
import onlytrade.app.viewmodel.trades.ui.state.TradeDetailUiState.OfferRejected
import onlytrade.app.viewmodel.trades.ui.state.TradeDetailUiState.OfferWithdrawn
import onlytrade.app.viewmodel.trades.ui.state.TradeDetailUiState.RejectingOffer
import onlytrade.app.viewmodel.trades.ui.state.TradeDetailUiState.WithdrawingOffer
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_name
import onlytrade.composeapp.generated.resources.home_5
import onlytrade.composeapp.generated.resources.ok
import onlytrade.composeapp.generated.resources.productDetail_3
import onlytrade.composeapp.generated.resources.productDetail_5
import onlytrade.composeapp.generated.resources.search
import onlytrade.composeapp.generated.resources.tradeDetail_1
import onlytrade.composeapp.generated.resources.tradeDetail_10
import onlytrade.composeapp.generated.resources.tradeDetail_11
import onlytrade.composeapp.generated.resources.tradeDetail_12
import onlytrade.composeapp.generated.resources.tradeDetail_13
import onlytrade.composeapp.generated.resources.tradeDetail_3
import onlytrade.composeapp.generated.resources.tradeDetail_4
import onlytrade.composeapp.generated.resources.tradeDetail_5
import onlytrade.composeapp.generated.resources.tradeDetail_6
import onlytrade.composeapp.generated.resources.tradeDetail_7
import onlytrade.composeapp.generated.resources.tradeDetail_8
import onlytrade.composeapp.generated.resources.tradeDetail_9
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random

class TradeDetailScreen(private val offer: Offer) : Screen {
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val sharedCMP = LocalSharedCMP.current
        val viewModel = koinViewModel<TradeDetailViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val imageUrls = offer.offeredProducts.flatMap { it.imageUrls }

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
                    contentDescription = imageUrls[page],
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
                val (tags, offerDetailLabel, products, buttons) = createRefs()


                Text(
                    text = stringResource(Res.string.tradeDetail_1),
                    modifier = Modifier.constrainAs(offerDetailLabel) {
                        top.linkTo(tags.bottom)
                        start.linkTo(tags.start)
                    }.padding(16.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = W500)
                )


                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                        .padding(bottom = 32.dp)
                        .constrainAs(products) {
                            top.linkTo(offerDetailLabel.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)

                        }
                ) {
                    items(offer.offeredProducts) {
                        ProductUI(sharedCMP, it)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Row(
                    modifier = Modifier.constrainAs(buttons) {
                        top.linkTo(products.bottom)
                        start.linkTo(products.start)
                        end.linkTo(products.end)
                    }.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // val noOfferOnProduct by remember(product.offers) { mutableStateOf(product.offers.isNullOrEmpty()) }

                    LaunchedEffect(Unit) {
                        viewModel.checkOffer(offer)
                    }

                    val madeOffer = viewModel.madeOffer(offer.offerMakerId)

                    //    val receivedOffer = viewModel.receivedOffer(offer.offerReceiverId)
                    if (madeOffer && offer.accepted) { // ui case where offer is accepted by receiver and viewed by maker.

                        OutlinedButton(
                            modifier = Modifier.weight(
                                1f
                            ),
                            onClick = {
                                //todo view contact info of offer receiver.
                            },
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(
                                1.dp, productDetailColorScheme.offerTradeBtnBorder
                            ),
                        ) {
                            Text(
                                text = stringResource(Res.string.tradeDetail_12),
                                color = productDetailColorScheme.offerTradeBtnText,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    } else if (madeOffer || uiState == WithdrawingOffer) OutlinedButton(
                        // ui case where offer is sent to receiver but can be withdrawn by maker.
                        modifier = if (uiState == WithdrawingOffer) Modifier.weight(1f)
                            .shimmer() else Modifier.weight(1f),
                        onClick = {
                            when (uiState) {
                                WithdrawingOffer -> {
                                    getToast().showToast("Withdrawing offer please wait")

                                }

                                OfferWithdrawn -> { // offer deleted click disabled.
                                    getToast().showToast("Offer deleted. please await refresh.")
                                }

                                else -> viewModel.withdrawOffer(offer.offerReceiverProductId)
                            }
                        },
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(
                            1.dp, productDetailColorScheme.offerTradeBtnBorder
                        ),
                    ) {
                        Text(
                            text = stringResource(if (uiState == WithdrawingOffer) Res.string.productDetail_5 else if (uiState == OfferWithdrawn) Res.string.tradeDetail_7 else Res.string.productDetail_3),
                            color = productDetailColorScheme.offerTradeBtnText,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    else { // ui case where offer is accepted and viewed by receiver.

                        LaunchedEffect(Unit) {
                            viewModel.checkOfferAccepted(offer.id)
                        }

                        val acceptedOffer =
                            offer.accepted || uiState == OfferAccepted

                        if (acceptedOffer.not()) {
                            val hideRejectBtn =
                                uiState == AcceptingOffer || uiState == OfferAccepted
                            val hideAcceptBtn =
                                uiState == RejectingOffer || uiState == OfferRejected

                            if (hideAcceptBtn.not())
                                Button(
                                    modifier = if (uiState == AcceptingOffer) Modifier.weight(1f)
                                        .shimmer() else Modifier.weight(1f),
                                    onClick = {
                                        when (uiState) {
                                            AcceptingOffer -> {
                                                getToast().showToast("Accepting offer please wait")

                                            }

                                            OfferAccepted -> {
                                                getToast().showToast("Offer accepted. please await refresh.")
                                            }

                                            else -> viewModel.acceptOffer(offer)
                                        }
                                    },
                                    shape = MaterialTheme.shapes.medium,
                                    colors = ButtonDefaults.buttonColors(productDetailColorScheme.buyProductBtn)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = stringResource(if (uiState == AcceptingOffer) Res.string.tradeDetail_5 else Res.string.tradeDetail_3),
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
                            if (hideRejectBtn.not())
                                OutlinedButton(
                                    modifier = if (uiState == RejectingOffer) Modifier.weight(1f)
                                        .shimmer() else Modifier.weight(1f),
                                    onClick = {
                                        when (uiState) {
                                            RejectingOffer -> {
                                                getToast().showToast("Rejecting offer please wait")

                                            }

                                            OfferRejected -> {
                                                getToast().showToast("Offer rejected. please await refresh.")
                                            }

                                            else -> viewModel.rejectOffer(offer)
                                        }
                                    },
                                    shape = MaterialTheme.shapes.medium,
                                    border = BorderStroke(
                                        1.dp, productDetailColorScheme.offerTradeBtnBorder
                                    ),
                                ) {
                                    Text(
                                        text = stringResource(if (uiState == RejectingOffer) Res.string.tradeDetail_6 else Res.string.tradeDetail_4),
                                        color = productDetailColorScheme.offerTradeBtnText,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                        } else { // offer accepted.
                            var showCompleteBtnDialog by remember { mutableStateOf(false) }
                            if (uiState is OfferCompleteApiError) {
                                //todo show offer complete api fail.
                            }
                            Button(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    //todo open leave comments.
                                    getToast().showToast("Under Development")
                                },
                                shape = MaterialTheme.shapes.medium,
                                colors = ButtonDefaults.buttonColors(productDetailColorScheme.buyProductBtn)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = stringResource(Res.string.tradeDetail_10),
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
                            OutlinedButton(
                                modifier = Modifier.weight(
                                    1f
                                ),
                                onClick = {
                                    showCompleteBtnDialog = true
                                },
                                shape = MaterialTheme.shapes.medium,
                                border = BorderStroke(
                                    1.dp, productDetailColorScheme.offerTradeBtnBorder
                                ),
                            ) {
                                Text(
                                    text = stringResource(Res.string.tradeDetail_12),
                                    color = productDetailColorScheme.offerTradeBtnText,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                            if (showCompleteBtnDialog)
                                AlertDialog(
                                    onDismissRequest = { showCompleteBtnDialog = false },
                                    title = { Text(stringResource(Res.string.tradeDetail_11)) },
                                    text = { //todo show contact detail of offer maker.
                                        Text("Offer maker's contact details show here")
                                    },
                                    dismissButton = {
                                        Button(
                                            shape = MaterialTheme.shapes.medium,
                                            colors = ButtonDefaults.buttonColors(
                                                productDetailColorScheme.buyProductBtn
                                            ), onClick = { showCompleteBtnDialog = false }) {
                                            Text(stringResource(Res.string.tradeDetail_13))
                                        }
                                    },
                                    confirmButton = {
                                        OutlinedButton(
                                            modifier = if (uiState == CompletingOffer) Modifier.shimmer() else Modifier,
                                            border = BorderStroke(
                                                1.dp, productDetailColorScheme.offerTradeBtnBorder
                                            ),
                                            onClick = {
                                                when (uiState) {
                                                    CompletingOffer -> {
                                                        getToast().showToast("Completing trade please wait")

                                                    }

                                                    OfferCompleted -> {
                                                        getToast().showToast("Trade Completed. please await refresh.")
                                                        showCompleteBtnDialog = false
                                                    }

                                                    else -> viewModel.completeOffer(offer)
                                                }
                                            },
                                            shape = MaterialTheme.shapes.medium,
                                        ) {
                                            Text(
                                                text = stringResource(if (uiState == CompletingOffer) Res.string.tradeDetail_8 else Res.string.tradeDetail_9),
                                                color = productDetailColorScheme.offerTradeBtnText
                                            )
                                        }
                                    }
                                )

                        }
                    }

                }

            }
        }
    }

    @Composable
    private fun ProductUI(sharedCMP: SharedCMP, product: Product? = null) {
        val size = (sharedCMP.screenWidth / 2).dp
        val nav = LocalNavigator.currentOrThrow
        Column(modifier = if (product == null) Modifier.shimmer() else Modifier.clickable {
            nav.push(ProductDetailScreen(product, tradeView = true))
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

                AsyncImage(
                    modifier = Modifier.matchParentSize().clip(MaterialTheme.shapes.extraLarge),
                    model = product?.imageUrls?.get(0),
                    contentDescription = product?.name,
                    contentScale = ContentScale.Crop
                )

                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            shape = CircleShape, color = MaterialTheme.colorScheme.onSecondary
                        )
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = stringResource(Res.string.search)
                )


            }

            ConstraintLayout(
                modifier = Modifier.padding(vertical = 16.dp)
            ) {

                val (c1, c2, c3, s1, s2, colorsTxt, productName, price) = createRefs()

                AsyncImage(
                    model = product?.imageUrls?.get(1),
                    contentDescription = product?.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape)
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

                Spacer(
                    modifier = Modifier
                        .width(12.dp)
                        .constrainAs(s1) {
                            top.linkTo(c1.top)
                            bottom.linkTo(c1.bottom)
                            start.linkTo(c1.start)
                            end.linkTo(c1.end)

                        })

                AsyncImage(
                    model = product?.imageUrls?.get(2),
                    contentDescription = product?.name,
                    contentScale = ContentScale.Crop, modifier = Modifier.clip(CircleShape)
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

                Spacer(
                    modifier = Modifier
                        .width(12.dp)
                        .constrainAs(s2) {
                            top.linkTo(c2.top)
                            bottom.linkTo(c2.bottom)
                            start.linkTo(c2.start)
                            end.linkTo(c2.end)

                        })
                AsyncImage(
                    model = product?.imageUrls?.get(3),
                    contentDescription = product?.name,
                    contentScale = ContentScale.Crop, modifier = Modifier.clip(CircleShape)
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

                Text(
                    modifier = Modifier
                        .constrainAs(colorsTxt) {
                            top.linkTo(parent.top)
                            start.linkTo(c3.end)

                        }
                        .padding(horizontal = 16.dp),
                    textDecoration = TextDecoration.Underline,
                    text = if (product == null) stringResource(Res.string.home_5) else
                        "All ${product.imageUrls.size} images",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = W300))

                Text(
                    modifier = Modifier
                        .constrainAs(productName) {
                            top.linkTo(c1.bottom)
                            start.linkTo(parent.start)
                        }
                        .padding(top = 16.dp),
                    text = product?.name ?: stringResource(Res.string.home_5),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = W500))

                Text(
                    modifier = Modifier.constrainAs(price) {
                        top.linkTo(productName.bottom)
                        start.linkTo(productName.start)
                    },
                    text = product?.estPrice?.toString() ?: stringResource(Res.string.home_5),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = W500)
                )

                /*  Text(
                      modifier = Modifier.constrainAs(discountPrice) {
                          top.linkTo(price.bottom)
                          start.linkTo(price.start)

                      },
                      textDecoration = TextDecoration.LineThrough,
                      text = "$${Random.nextInt(index, 500)}",
                      style = MaterialTheme.typography.titleSmall.copy(fontWeight = W300)
                  )*/
            }
        }
    }

}