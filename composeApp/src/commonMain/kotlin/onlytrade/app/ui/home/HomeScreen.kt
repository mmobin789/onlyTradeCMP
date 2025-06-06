package onlytrade.app.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.W200
import androidx.compose.ui.text.font.FontWeight.Companion.W300
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextAlign
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
import onlytrade.app.ui.home.categories.sub.SubCategoriesScreen
import onlytrade.app.ui.home.colorScheme.homeColorScheme
import onlytrade.app.ui.home.products.ProductsScreen
import onlytrade.app.ui.home.products.add.AddProductScreen
import onlytrade.app.ui.home.products.details.ProductCache
import onlytrade.app.ui.home.products.details.ProductDetailScreen
import onlytrade.app.ui.home.products.my.MyProductsScreen
import onlytrade.app.ui.home.profile.ProfileScreen
import onlytrade.app.ui.home.trades.MyTradesScreen
import onlytrade.app.viewmodel.home.ui.HomeUiState.GetProductsApiError
import onlytrade.app.viewmodel.home.ui.HomeUiState.LoadingProducts
import onlytrade.app.viewmodel.home.ui.HomeUiState.ProductsNotFound
import onlytrade.app.viewmodel.home.ui.HomeViewModel
import onlytrade.app.viewmodel.product.repository.data.db.Product
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_logo
import onlytrade.composeapp.generated.resources.app_name
import onlytrade.composeapp.generated.resources.botBar_1
import onlytrade.composeapp.generated.resources.botBar_2
import onlytrade.composeapp.generated.resources.botBar_3
import onlytrade.composeapp.generated.resources.botBar_4
import onlytrade.composeapp.generated.resources.home_1
import onlytrade.composeapp.generated.resources.home_2
import onlytrade.composeapp.generated.resources.home_3
import onlytrade.composeapp.generated.resources.home_4
import onlytrade.composeapp.generated.resources.home_5
import onlytrade.composeapp.generated.resources.home_6
import onlytrade.composeapp.generated.resources.ic_quickmart_intro
import onlytrade.composeapp.generated.resources.ic_quickmart_intro_dark
import onlytrade.composeapp.generated.resources.outline_compare_arrows_24
import onlytrade.composeapp.generated.resources.search
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<HomeViewModel>()
        val userLoggedIn by remember { mutableStateOf(viewModel.isUserLoggedIn) }
        val products by viewModel.productList.collectAsStateWithLifecycle()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val sharedCMP = LocalSharedCMP.current
        val nav = LocalNavigator.currentOrThrow
        val productGridState = rememberLazyGridState()
        val headerVisible = productGridState.canScrollBackward.not()
        Scaffold(topBar = {
            Column(modifier = if (uiState is LoadingProducts) Modifier.shimmer() else Modifier) {
                //   AnimatedVisibility(visible = headerVisible.not()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(homeColorScheme.topSearchBarBG)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    AsyncImage(
                        model = if (isSystemInDarkTheme()) Res.drawable.ic_quickmart_intro_dark else Res.drawable.ic_quickmart_intro,
                        contentScale = ContentScale.None,
                        contentDescription = stringResource(Res.string.app_logo),
                        modifier = Modifier.padding(top = 32.dp)
                    )

                    Row(
                        modifier = Modifier
                            .padding(top = 32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = stringResource(Res.string.search)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = stringResource(Res.string.search)
                        )
                    }
                }


                /*   SearchBar(
                       inputField = {
                           OTTextField(
                               label = "",
                               value = TextFieldValue(""),
                               onValueChange = { },
                               isError = false,
                               trailingIcon = {

                                   Icon(
                                       painter = painterResource(R.drawable.outline_clear_24),
                                       tint = MaterialTheme.colorScheme.secondary,
                                       contentDescription = null,
                                       modifier = Modifier.clickable { }
                                   )

                               },
                               keyboardType = KeyboardType.Text, imeAction = ImeAction.Search
                           )
                       },
                       expanded = isSearchBarExtended,
                       onExpandedChange = {}
                   ) { }*/
                AnimatedVisibility(visible = headerVisible) {
                    Box(
                        modifier = Modifier
                            .background(homeColorScheme.pagerCardBG)
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        val pagerState = rememberPagerState {
                            if (products.isEmpty()) 5 else products.size
                        }

                        // Auto-scroll logic for banner.
                        if (products.isNotEmpty())
                            LaunchedEffect(uiState !is LoadingProducts) {
                                while (headerVisible) {
                                    val nextPage =
                                        (pagerState.currentPage + 1) % pagerState.pageCount
                                    pagerState.animateScrollToPage(nextPage)
                                    delay(3000) // Delay between scrolls (3 seconds)
                                }
                            }

                        HorizontalPager(
                            state = pagerState
                        ) {

                            val randomProduct by remember { mutableStateOf(randomProduct(products)) }

                            AsyncImage(
                                model = randomProduct?.first,
                                contentDescription = randomProduct?.second?.description,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.clickable {
                                    randomProduct?.second?.let {
                                        ProductCache.add(it)
                                        nav.push(ProductDetailScreen(it.id))
                                    }
                                }.clip(MaterialTheme.shapes.medium)
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
                            totalDots = pagerState.pageCount,
                            selectedIndex = pagerState.currentPage,
                            selectedColor = MaterialTheme.colorScheme.tertiary,
                            unSelectedColor = Color(0xFFC0C0C0)
                        )

                    }
                }
            }
        }, bottomBar = {
            if (userLoggedIn)
                Row(
                    modifier = Modifier
                        .background(homeColorScheme.botBarBG)
                        .padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(Modifier.weight(1f)) {
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
                    Column(Modifier.weight(1f).clickable {
                        nav.push(MyTradesScreen())
                    }) {
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
                            .clickable {
                                nav.push(MyProductsScreen())
                            }) {

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
                        Modifier
                            .weight(1f)
                            .clickable { nav.push(ProfileScreen()) }
                    ) {

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

        }, floatingActionButton = {
            if (userLoggedIn) {

                val addProductClicked = {
                    nav.push(AddProductScreen())
                }

                if (productGridState.isScrollInProgress)
                    FloatingActionButton(
                        onClick = addProductClicked,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.secondary
                    ) {
                        Icon(Icons.Filled.Add, stringResource(Res.string.home_4))
                    }
                else
                    ExtendedFloatingActionButton(
                        onClick = addProductClicked,
                        icon = { Icon(Icons.Outlined.Add, stringResource(Res.string.home_4)) },
                        text = { Text(text = stringResource(Res.string.home_4)) },
                    )
            }

        }) { paddingValues ->

            LaunchedEffect(Unit) {
                viewModel.refreshHomePage()
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(homeColorScheme.screenBG)
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                AnimatedVisibility(visible = headerVisible) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Box {
                            Text(
                                text = stringResource(Res.string.home_1),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = W700)
                            )


                            /*   Text(
                        modifier = Modifier.align(Alignment.TopEnd),
                        text = "SEE All",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = W700)
                    )*/
                        }

                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 16.dp),
                            horizontalArrangement = Arrangement.Center
                            // verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(4) { i ->

                                Column(
                                    modifier = Modifier.clickable {
                                        nav.push(
                                            SubCategoriesScreen(
                                                "Category ${i + 1}"
                                            )
                                        )
                                    },
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceAround
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.FavoriteBorder,
                                        contentDescription = stringResource(Res.string.app_name)
                                    )

                                    Text(
                                        text = "Category ${i + 1}",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = W500)
                                    )

                                }
                                if (i < 3) Spacer(modifier = Modifier.width(16.dp))

                            }

                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.TopStart),
                        text = stringResource(Res.string.home_2),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = W700)
                    )


                    Text(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clickable {
                                nav.push(ProductsScreen())
                            },
                        text = stringResource(Res.string.home_3),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = W700)
                    )
                }

                LaunchedEffect(productGridState) {
                    snapshotFlow { productGridState.layoutInfo }.collect { layoutInfo ->
                        val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                        val total = layoutInfo.totalItemsCount
                        if (lastVisible >= total - viewModel.productPageSizeExpected / 2) {
                            viewModel.getProducts()

                        }
                    }
                }

                if (uiState == ProductsNotFound) {
                    Text(
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally).clickable {
                            viewModel.reloadProducts()
                        },
                        fontSize = 20.sp,
                        text = stringResource(Res.string.home_6),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = W500)
                    )
                } else if (uiState is GetProductsApiError) {
                    Text(
                        textAlign = TextAlign.Center,
                        modifier = Modifier,
                        fontSize = 20.sp,
                        text = (uiState as GetProductsApiError).error,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = W500)
                    )
                }

                LazyVerticalGrid(
                    state = productGridState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    columns = GridCells.Fixed(2)
                ) {

                    items(products) { product ->
                        ProductUI(sharedCMP, product)
                    }

                    if (uiState == LoadingProducts) items(viewModel.productPageSizeExpected) {
                        ProductUI(sharedCMP)
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
        ProductCache.add(product)
        nav.push(ProductDetailScreen(product.id))
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

private fun randomProduct(products: List<Product>): Pair<String, Product>? {
    if (products.isEmpty())
        return null
    val randomProduct = products[Random.nextInt(0, products.size)]
    val randomProductImages = randomProduct.imageUrls
    val randomProductImageCount = randomProductImages.size
    return Pair(
        randomProductImages[Random.nextInt(0, randomProductImageCount)],
        randomProduct
    )
}