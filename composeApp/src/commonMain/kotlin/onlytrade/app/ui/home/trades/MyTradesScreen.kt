package onlytrade.app.ui.home.trades

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.W200
import androidx.compose.ui.text.font.FontWeight.Companion.W300
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay
import onlytrade.app.ui.design.components.DotsIndicator
import onlytrade.app.ui.design.components.LocalSharedCMP
import onlytrade.app.ui.design.components.SharedCMP
import onlytrade.app.ui.design.components.getToast
import onlytrade.app.ui.home.products.my.colorScheme.myProductsColorScheme
import onlytrade.app.ui.home.profile.ProfileScreen
import onlytrade.app.viewmodel.product.offer.repository.data.db.Offer
import onlytrade.app.viewmodel.product.repository.data.db.Product
import onlytrade.app.viewmodel.trades.ui.MyTradesViewModel
import onlytrade.app.viewmodel.trades.ui.state.MyTradesUiState.Idle
import onlytrade.app.viewmodel.trades.ui.state.MyTradesUiState.LoadingOffersMade
import onlytrade.app.viewmodel.trades.ui.state.MyTradesUiState.LoadingOffersReceived
import onlytrade.app.viewmodel.trades.ui.state.MyTradesUiState.NoOffersMade
import onlytrade.app.viewmodel.trades.ui.state.MyTradesUiState.NoOffersReceived
import onlytrade.app.viewmodel.trades.ui.state.MyTradesUiState.OfferDeleteApiError
import onlytrade.app.viewmodel.trades.ui.state.MyTradesUiState.OfferDeleted
import onlytrade.app.viewmodel.trades.ui.state.MyTradesUiState.OfferNotFound
import onlytrade.app.viewmodel.trades.ui.state.MyTradesUiState.OffersMade
import onlytrade.app.viewmodel.trades.ui.state.MyTradesUiState.OffersMadeError
import onlytrade.app.viewmodel.trades.ui.state.MyTradesUiState.OffersReceived
import onlytrade.app.viewmodel.trades.ui.state.MyTradesUiState.OffersReceivedError
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_name
import onlytrade.composeapp.generated.resources.botBar_2
import onlytrade.composeapp.generated.resources.botBar_3
import onlytrade.composeapp.generated.resources.cancel
import onlytrade.composeapp.generated.resources.home_5
import onlytrade.composeapp.generated.resources.myTrades_1
import onlytrade.composeapp.generated.resources.myTrades_2
import onlytrade.composeapp.generated.resources.outline_compare_arrows_24
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random

class MyTradesScreen : Screen {

    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val offerListState = rememberLazyListState()
        val headerVisible = offerListState.canScrollBackward.not()
        val sharedCMP = LocalSharedCMP.current
        val viewModel = koinViewModel<MyTradesViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        var offerSentBtn by remember { mutableStateOf(true) }
        var offerReceivedBtn by remember { mutableStateOf(false) }
        var refreshTrades by remember { mutableStateOf(false) } //todo change to true on error user action.
        Scaffold(topBar = {
            AnimatedVisibility(visible = headerVisible) {
                Column {
                    Box(
                        modifier = Modifier.background(myProductsColorScheme.myProductsBarBG)
                            .fillMaxWidth().padding(16.dp)
                    ) {

                        Row(modifier = Modifier.align(Alignment.CenterStart)) {
                            Icon(
                                modifier = Modifier.clickable { nav.pop() },
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = stringResource(Res.string.cancel)
                            )

                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = stringResource(Res.string.botBar_2),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = W700)
                            )
                        }

                    }

                    Spacer(
                        modifier = Modifier.background(myProductsColorScheme.myProductsBarBG)
                            .height(1.dp).fillMaxWidth()
                    )

                }
            }
        }, bottomBar = {
            Row(
                modifier = Modifier.background(myProductsColorScheme.botBarBG).padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    Modifier.weight(1f).clickable {
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
                        text = stringResource(Res.string.botBar_3),
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = W200)
                    )
                }
                Column(
                    Modifier.weight(1f).clickable { nav.push(ProfileScreen()) }) {

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

            Column(
                modifier = Modifier.padding(paddingValues)
                    .background(myProductsColorScheme.screenBG)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
                        .border(
                            width = 1.dp,
                            color = myProductsColorScheme.buySellTabBGOutline,
                            shape = MaterialTheme.shapes.large
                        ).padding(8.dp)
                ) {
                    Text(
                        fontSize = 15.sp,
                        modifier = Modifier.clickable {
                            offerSentBtn = true
                            offerReceivedBtn = false
                            viewModel.getOffersMade()
                        }.padding(horizontal = 16.dp),
                        text = stringResource(Res.string.myTrades_1),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = W300)
                    )
                    Text(
                        fontSize = 15.sp,
                        modifier = Modifier.clickable {
                            offerSentBtn = false
                            offerReceivedBtn = true
                            viewModel.getOffersReceived()
                        }.padding(horizontal = 16.dp),
                        text = stringResource(Res.string.myTrades_2),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = W300)
                    )
                }


                LazyColumn(
                    state = offerListState,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    when (uiState) {
                        LoadingOffersMade -> items(20) {
                            OfferUI(offerListState, viewModel, sharedCMP)
                        }

                        is OffersMade -> items((uiState as OffersMade).offers) {
                            OfferUI(offerListState, viewModel, sharedCMP, it)
                        }

                        LoadingOffersReceived -> items(20) {
                            OfferUI(offerListState, viewModel, sharedCMP)
                        }

                        is OffersReceived -> items((uiState as OffersReceived).offers) {
                            OfferUI(offerListState, viewModel, sharedCMP, it)
                        }

                        is OffersMadeError -> { //todo show error.
                            getToast().showToast((uiState as OffersMadeError).error)
                        }

                        NoOffersMade -> {
                            getToast().showToast("No Offers made.")
                            viewModel.idle()
                        }

                        NoOffersReceived -> {
                            getToast().showToast("No Offers received.")
                            viewModel.idle()
                        }

                        is OffersReceivedError -> {
                            getToast().showToast((uiState as OffersReceivedError).error)
                            viewModel.idle()
                        }

                        OfferDeleted -> {
                            getToast().showToast("Offer withdrawn successfully!")
                        }

                        OfferNotFound -> {
                            getToast().showToast("Offer not found.")
                        }

                        is OfferDeleteApiError -> {
                            getToast().showToast((uiState as OfferDeleteApiError).error)
                        }

                        Idle -> {} // do nothing.
                    }
                }
            }
        }


    }


    @Composable
    private fun OfferUI(
        offerListState: LazyListState,
        viewModel: MyTradesViewModel,
        sharedCMP: SharedCMP,
        offer: Offer? = null
    ) {
        val nav = LocalNavigator.currentOrThrow
        val offeredProducts by remember { mutableStateOf(offer?.offeredProducts ?: emptyList()) }

        Column(
            modifier = if (offer == null) Modifier.shimmer() else Modifier.fillMaxWidth()
                .clickable {
                    //todo view offer details.
                }) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                val pagerState = rememberPagerState {
                    if (offeredProducts.isEmpty()) 5 else {
                        var totalImages = 0
                        offeredProducts.map { it.imageUrls }.forEach {
                            totalImages += it.size
                        }
                        totalImages
                    }
                }

                // Auto-scroll logic for banner.
                LaunchedEffect(offer != null) {
                    while (offer != null) {
                        val nextPage =
                            (pagerState.currentPage + 1) % pagerState.pageCount
                        pagerState.animateScrollToPage(nextPage)
                        delay(3000) // Delay between scrolls (3 seconds)
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false
                ) {


                    AsyncImage(
                        model = randomProductImage(offeredProducts),
                        contentDescription = offer?.offeredProductIds.toString(),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(MaterialTheme.shapes.medium)
                            .background(
                                color = Color(
                                    Random.nextFloat(),
                                    Random.nextFloat(),
                                    Random.nextFloat()
                                ), shape = MaterialTheme.shapes.medium
                            )
                            .fillMaxWidth()
                            .height((sharedCMP.screenHeight / 4).dp)
                    )


                }
                DotsIndicator(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(horizontal = 8.dp)
                        .align(Alignment.BottomEnd),
                    totalDots = pagerState.pageCount.let {
                        if (it > 20)
                            20 else it
                    },
                    selectedIndex = pagerState.currentPage,
                    selectedColor = MaterialTheme.colorScheme.tertiary,
                    unSelectedColor = Color(0xFFC0C0C0)
                )

            }

            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {


                /* Text(
                     modifier = Modifier,
                     text = product?.name ?: stringResource(Res.string.home_5),
                     style = MaterialTheme.typography.titleMedium.copy(fontWeight = W500)
                 )

                 Text(
                     modifier = Modifier,
                     text = product?.estPrice?.toString() ?: stringResource(Res.string.home_5),
                     style = MaterialTheme.typography.titleMedium.copy(fontWeight = W500)
                 )*/

                /*  Text(
                      modifier = Modifier,
                      textDecoration = TextDecoration.LineThrough,
                      text = "$${Random.nextInt(index, 500)}",
                      style = MaterialTheme.typography.titleSmall.copy(fontWeight = W300)
                  )*/

                ConstraintLayout(
                    modifier = Modifier
                ) {

                    val (c1, c2, c3, s1, s2, colorsTxt) = createRefs()

                    AsyncImage(
                        model = randomProductImage(offeredProducts),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape).constrainAs(c1) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }.size(24.dp).background(
                            shape = CircleShape, color = Color(
                                Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                            )
                        )
                    )

                    Spacer(
                        modifier = Modifier.width(12.dp).constrainAs(s1) {
                            top.linkTo(c1.top)
                            bottom.linkTo(c1.bottom)
                            start.linkTo(c1.start)
                            end.linkTo(c1.end)

                        })

                    AsyncImage(
                        model = randomProductImage(offeredProducts),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape).constrainAs(c2) {
                            start.linkTo(s1.end)
                            top.linkTo(parent.top)
                        }.size(24.dp).background(
                            shape = CircleShape, color = Color(
                                Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                            )
                        )
                    )

                    Spacer(
                        modifier = Modifier.width(12.dp).constrainAs(s2) {
                            top.linkTo(c2.top)
                            bottom.linkTo(c2.bottom)
                            start.linkTo(c2.start)
                            end.linkTo(c2.end)

                        })
                    AsyncImage(
                        model = randomProductImage(offeredProducts),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape).constrainAs(c3) {
                            start.linkTo(s2.end)
                            top.linkTo(parent.top)
                        }.size(24.dp).background(
                            shape = CircleShape, color = Color(
                                Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                            )
                        )
                    )

                    Text(
                        modifier = Modifier.constrainAs(colorsTxt) {
                            top.linkTo(parent.top)
                            start.linkTo(c3.end)

                        }.padding(horizontal = 16.dp),
                        textDecoration = TextDecoration.Underline,
                        text = if (offeredProducts.isEmpty()) stringResource(Res.string.home_5) else "View all ${offeredProducts.size} products",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = W300)
                    )
                }
            }


        }
    }

    private fun randomProductImage(products: List<Product>?): String? {
        if (products.isNullOrEmpty())
            return null
        val randomProduct = products[Random.nextInt(0, products.size)]
        val randomProductImages = randomProduct.imageUrls
        val randomProductImageCount = randomProductImages.size
        return randomProductImages[Random.nextInt(0, randomProductImageCount)]


    }
}