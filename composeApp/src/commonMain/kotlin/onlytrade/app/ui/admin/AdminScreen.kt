package onlytrade.app.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W300
import androidx.compose.ui.text.font.FontWeight.Companion.W500
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
import onlytrade.app.ui.design.components.LocalSharedCMP
import onlytrade.app.ui.design.components.SharedCMP
import onlytrade.app.ui.home.colorScheme.homeColorScheme
import onlytrade.app.ui.home.products.ProductsScreen
import onlytrade.app.ui.home.products.details.ProductCache
import onlytrade.app.ui.home.products.details.ProductDetailScreen
import onlytrade.app.viewmodel.admin.AdminViewModel
import onlytrade.app.viewmodel.admin.ui.AdminUiState.GetApprovalProductsApiError
import onlytrade.app.viewmodel.admin.ui.AdminUiState.GetApprovalUsersApiError
import onlytrade.app.viewmodel.admin.ui.AdminUiState.LoadingProducts
import onlytrade.app.viewmodel.admin.ui.AdminUiState.LoadingUsers
import onlytrade.app.viewmodel.admin.ui.AdminUiState.ProductsNotFound
import onlytrade.app.viewmodel.admin.ui.AdminUiState.UsersNotFound
import onlytrade.app.viewmodel.login.repository.data.db.User
import onlytrade.app.viewmodel.product.repository.data.db.Product
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.ad_1
import onlytrade.composeapp.generated.resources.ad_2
import onlytrade.composeapp.generated.resources.ad_3
import onlytrade.composeapp.generated.resources.app_name
import onlytrade.composeapp.generated.resources.home_2
import onlytrade.composeapp.generated.resources.home_3
import onlytrade.composeapp.generated.resources.home_5
import onlytrade.composeapp.generated.resources.home_6
import onlytrade.composeapp.generated.resources.outline_compare_arrows_24
import onlytrade.composeapp.generated.resources.search
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

                if (uiState == ProductsNotFound) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center).clickable {
                                viewModel.getProducts()
                            },
                            fontSize = 20.sp,
                            text = stringResource(Res.string.home_6),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W500)
                        )
                    }
                } else if (uiState is GetApprovalProductsApiError) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center).clickable {
                                viewModel.getProducts()
                            },
                            fontSize = 20.sp,
                            text = (uiState as GetApprovalProductsApiError).error,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W500)
                        )
                    }
                } else if (uiState == UsersNotFound) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center).clickable {
                                viewModel.getUsers()
                            },
                            fontSize = 20.sp,
                            text = stringResource(Res.string.home_6),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W500)
                        )
                    }
                } else if (uiState is GetApprovalUsersApiError) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center).clickable {
                                viewModel.getProducts()
                            },
                            fontSize = 20.sp,
                            text = (uiState as GetApprovalProductsApiError).error,
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

                    items(users) { user ->
                        UserUI(sharedCMP, user)
                    }

                    if (uiState == LoadingProducts) items(20) {
                        ProductUI(sharedCMP)
                    }
                    if (uiState == LoadingUsers) items(20) {
                        UserUI(sharedCMP)
                    }

                }


            }
        }
    }

    @Composable
    private fun ProductUI(sharedCMP: SharedCMP, product: Product? = null) {
        val size = sharedCMP.screenWidth / 2
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

            }
        }
    }

    @Composable
    private fun UserUI(sharedCMP: SharedCMP, user: User? = null) {
        val size = sharedCMP.screenWidth / 2
        // val nav = LocalNavigator.currentOrThrow
        Column(modifier = if (user == null) Modifier.shimmer() else Modifier.clickable {
            // nav.push(ProductDetailScreen(product.id))
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
                    model = user?.photo,
                    contentDescription = user?.name,
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

                val (c1, s1, email, userName, phone) = createRefs()

                AsyncImage(
                    model = user?.photoId,
                    contentDescription = user?.name,
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


                Text(
                    modifier = Modifier
                        .constrainAs(email) {
                            top.linkTo(parent.top)
                            start.linkTo(c1.end)

                        }
                        .padding(horizontal = 16.dp),
                    textDecoration = TextDecoration.Underline,
                    text = user?.email ?: stringResource(Res.string.home_5),
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = W300))

                Text(
                    modifier = Modifier
                        .constrainAs(userName) {
                            top.linkTo(c1.bottom)
                            start.linkTo(parent.start)
                        }
                        .padding(top = 16.dp),
                    text = user?.name ?: stringResource(Res.string.home_5),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = W500))

                Text(
                    modifier = Modifier.constrainAs(phone) {
                        top.linkTo(userName.bottom)
                        start.linkTo(userName.start)
                    },
                    text = user?.phone ?: stringResource(Res.string.home_5),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = W500)
                )

            }
        }
    }
}