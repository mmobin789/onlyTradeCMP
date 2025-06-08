package onlytrade.app.ui.admin

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AccountCircle
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay
import onlytrade.app.ui.design.components.DotsIndicator
import onlytrade.app.ui.design.components.LocalSharedCMP
import onlytrade.app.ui.home.ProductUI
import onlytrade.app.ui.home.categories.sub.SubCategoriesScreen
import onlytrade.app.ui.home.colorScheme.homeColorScheme
import onlytrade.app.ui.home.products.ProductsScreen
import onlytrade.app.ui.home.products.add.AddProductScreen
import onlytrade.app.ui.home.products.details.ProductCache
import onlytrade.app.ui.home.products.details.ProductDetailScreen
import onlytrade.app.ui.home.products.my.MyProductsScreen
import onlytrade.app.ui.home.profile.ProfileScreen
import onlytrade.app.ui.home.randomProduct
import onlytrade.app.ui.home.trades.MyTradesScreen
import onlytrade.app.viewmodel.admin.AdminViewModel
import onlytrade.app.viewmodel.home.ui.HomeUiState
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
import onlytrade.composeapp.generated.resources.home_6
import onlytrade.composeapp.generated.resources.ic_quickmart_intro
import onlytrade.composeapp.generated.resources.ic_quickmart_intro_dark
import onlytrade.composeapp.generated.resources.outline_compare_arrows_24
import onlytrade.composeapp.generated.resources.search
import onlytrade.composeapp.generated.resources.tradeDetail_17
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random

class AdminScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<AdminViewModel>()
        val products by viewModel.productsUiState.collectAsStateWithLifecycle()
        val users by viewModel.usersUiState.collectAsStateWithLifecycle()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val sharedCMP = LocalSharedCMP.current
        val nav = LocalNavigator.currentOrThrow
        val productGridState = rememberLazyGridState()
        val headerVisible = productGridState.canScrollBackward.not()
        Scaffold(topBar = {
            Column {
                //   AnimatedVisibility(visible = headerVisible.not()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(homeColorScheme.topSearchBarBG)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = stringResource(Res.string.ad_1),
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = W500)
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


            }
        }, bottomBar = {
                Row(
                    modifier = Modifier
                        .background(homeColorScheme.botBarBG)
                        .padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(Modifier.weight(1f).clickable {
viewModel.getUsers()
                    }) {
                        Icon(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = stringResource(Res.string.app_name)
                        )

                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = stringResource(Res.string.ad_2),
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.W200)
                        )
                    }
                    Column(Modifier.weight(1f).clickable {
viewModel.getProducts()
                    }) {
                        Icon(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            imageVector = vectorResource(Res.drawable.outline_compare_arrows_24),
                            contentDescription = stringResource(Res.string.app_name)
                        )

                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = stringResource(Res.string.ad_3),
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.W200)
                        )
                    }
                }

        }) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(homeColorScheme.screenBG)
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                AnimatedVisibility(visible = headerVisible) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Box {
                            Text(
                                text = stringResource(Res.string.home_1),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W700)
                            )

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
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W700)
                    )


                    Text(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clickable {
                                nav.push(ProductsScreen())
                            },
                        text = stringResource(Res.string.home_3),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W700)
                    )
                }

                if (uiState == HomeUiState.ProductsNotFound) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center).clickable {
                                viewModel.reloadProducts()
                            },
                            fontSize = 20.sp,
                            text = stringResource(Res.string.home_6),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W500)
                        )
                    }
                } else if (uiState is HomeUiState.GetProductsApiError) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center).clickable {
                                viewModel.reloadProducts()
                            },
                            fontSize = 20.sp,
                            text = (uiState as HomeUiState.GetProductsApiError).error,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W500)
                        )
                    }
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

                    if (uiState == HomeUiState.LoadingProducts) items(viewModel.productPageSizeExpected) {
                        ProductUI(sharedCMP)
                    }


                }


            }
        }
    }
}