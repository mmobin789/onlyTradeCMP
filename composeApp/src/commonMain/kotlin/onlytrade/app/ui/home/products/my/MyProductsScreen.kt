package onlytrade.app.ui.home.products.my

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import com.valentinilk.shimmer.shimmer
import onlytrade.app.ui.design.components.LocalSharedCMP
import onlytrade.app.ui.design.components.SharedCMP
import onlytrade.app.ui.design.components.getToast
import onlytrade.app.ui.home.products.details.ProductDetailScreen
import onlytrade.app.ui.home.products.my.colorScheme.myProductsColorScheme
import onlytrade.app.ui.home.profile.ProfileScreen
import onlytrade.app.viewmodel.product.repository.data.db.Product
import onlytrade.app.viewmodel.product.ui.MyProductsViewModel
import onlytrade.app.viewmodel.product.ui.state.MyProductsUiState.GetProductsApiError
import onlytrade.app.viewmodel.product.ui.state.MyProductsUiState.Idle
import onlytrade.app.viewmodel.product.ui.state.MyProductsUiState.LoadingProducts
import onlytrade.app.viewmodel.product.ui.state.MyProductsUiState.ProductsNotFound
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_name
import onlytrade.composeapp.generated.resources.botBar_3
import onlytrade.composeapp.generated.resources.cancel
import onlytrade.composeapp.generated.resources.home_5
import onlytrade.composeapp.generated.resources.myProducts_1
import onlytrade.composeapp.generated.resources.myProducts_2
import onlytrade.composeapp.generated.resources.outline_compare_arrows_24
import onlytrade.composeapp.generated.resources.search
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random

class MyProductsScreen(private val productIdsCallback: ((HashSet<Long>) -> Unit)? = null) : Screen {

    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val productListState = rememberLazyListState()
        val headerVisible = productListState.canScrollBackward.not()
        val sharedCMP = LocalSharedCMP.current
        val viewModel = koinViewModel<MyProductsViewModel>()
        val products by viewModel.productList.collectAsStateWithLifecycle()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val selectionMode = productIdsCallback != null
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
                                text = stringResource(if (selectionMode) Res.string.myProducts_1 else Res.string.botBar_3),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = W700)
                            )
                        }

                        if (selectionMode) Text(
                            modifier = Modifier.align(Alignment.CenterEnd)
                                .clickable {
                                    productIdsCallback?.invoke(viewModel.pickedProductIds)
                                    nav.pop()
                                },
                            text = stringResource(Res.string.myProducts_2),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = W500)
                        )

                    }

                    Spacer(
                        modifier = Modifier.background(myProductsColorScheme.myProductsBarBG)
                            .height(1.dp).fillMaxWidth()
                    )

                }
            }
        }, bottomBar = {
            if (selectionMode.not())
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
                /* Row(
                     modifier = Modifier.padding(16.dp)
                         .border(
                             width = 1.dp,
                             color = myProductsColorScheme.buySellTabBGOutline,
                             shape = MaterialTheme.shapes.large
                         ).padding(8.dp)
                 ) {
                     Text(
                         fontSize = 15.sp,
                         modifier = Modifier.padding(horizontal = 16.dp),
                         text = stringResource(Res.string.myProducts_1),
                         style = MaterialTheme.typography.titleLarge.copy(fontWeight = W300)
                     )
                     Text(
                         fontSize = 15.sp,
                         modifier = Modifier.padding(horizontal = 16.dp),
                         text = stringResource(Res.string.myProducts_2),
                         style = MaterialTheme.typography.titleLarge.copy(fontWeight = W300)
                     )
                 }*/
                LaunchedEffect(productListState) {
                    snapshotFlow { productListState.layoutInfo }.collect { layoutInfo ->
                        val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                        val total = layoutInfo.totalItemsCount
                        if (lastVisible >= total - viewModel.productPageSizeExpected / 2) {
                            viewModel.getProducts()

                        }
                    }
                }


                LazyColumn(
                    state = productListState,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(products) { product ->
                        ProductUI(viewModel, sharedCMP, product, selectionMode)
                    }

                    when (uiState) {
                        LoadingProducts -> items(2) { i ->
                            ProductUI(viewModel, sharedCMP)
                        }

                        ProductsNotFound -> { //todo display error with call to action to reload products then call viewModel.getProducts( tryAgain = true) as action.
                            getToast().showToast("Products not found.")
                        }

                        is GetProductsApiError -> { //todo show error.

                        }

                        Idle -> {} // do nothing.

                    }
                }
            }
        }


    }


    @Composable
    private fun ProductUI(
        viewModel: MyProductsViewModel,
        sharedCMP: SharedCMP,
        product: Product? = null,
        selectionMode: Boolean = false
    ) {
        val size = (sharedCMP.screenWidth / 3).dp
        val nav = LocalNavigator.currentOrThrow
        var selected by remember { mutableStateOf(false) }
        Row(
            modifier = if (product == null) Modifier.shimmer() else Modifier.fillMaxWidth()
                .clickable {
                    val id = product.id
                    if (selectionMode) {
                        selected = viewModel.selectProduct(id)
                    } else nav.push(ProductDetailScreen(productId = id))
                }) {
            AsyncImage(
                model = product?.imageUrls?.get(0),
                contentDescription = product?.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(MaterialTheme.shapes.extraLarge).size(size).background(
                    color = Color(
                        Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                    ), shape = MaterialTheme.shapes.extraLarge
                )
            )

            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {


                Text(
                    modifier = Modifier,
                    text = product?.name ?: stringResource(Res.string.home_5),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = W500)
                )

                Text(
                    modifier = Modifier,
                    text = product?.estPrice?.toString() ?: stringResource(Res.string.home_5),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = W500)
                )

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
                        model = product?.imageUrls?.get(1),
                        contentDescription = product?.name,
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
                        model = product?.imageUrls?.get(2),
                        contentDescription = product?.name,
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
                        model = product?.imageUrls?.get(3),
                        contentDescription = product?.name,
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
                        text = if (product == null) stringResource(Res.string.home_5) else "All ${product.imageUrls.size} images",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = W300)
                    )
                }
            }



            Column(modifier = Modifier.fillMaxHeight()) {
                if (selected)
                    Icon(
                        modifier = Modifier.align(Alignment.Start),
                        imageVector = Icons.Outlined.Check,
                        contentDescription = stringResource(Res.string.search)
                    )
                if (selectionMode.not())
                    Icon(
                        modifier = Modifier.align(Alignment.End),
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = stringResource(Res.string.search)
                    )
            }
        }
    }
}