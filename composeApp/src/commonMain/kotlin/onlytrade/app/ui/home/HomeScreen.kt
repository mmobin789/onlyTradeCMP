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
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import coil3.compose.AsyncImage
import onlytrade.app.ui.design.components.DotsIndicator
import onlytrade.app.ui.design.components.ScreenSize
import onlytrade.app.ui.home.categories.sub.SubCategoriesScreen
import onlytrade.app.ui.home.colorScheme.homeColorScheme
import onlytrade.app.ui.home.products.ProductsScreen
import onlytrade.app.ui.home.products.add.AddProductScreen
import onlytrade.app.ui.home.products.details.ProductDetailScreen
import onlytrade.app.ui.home.wishlist.WishListScreen
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_logo
import onlytrade.composeapp.generated.resources.app_name
import onlytrade.composeapp.generated.resources.ic_quickmart_intro
import onlytrade.composeapp.generated.resources.ic_quickmart_intro_dark
import onlytrade.composeapp.generated.resources.outline_compare_arrows_24
import onlytrade.composeapp.generated.resources.search
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import kotlin.random.Random

class HomeScreen(private val screenSize: ScreenSize) : Screen {

    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val productGridState = rememberLazyGridState()
        val headerVisible = productGridState.canScrollBackward.not()
        //       var isSearchBarExtended by remember { mutableStateOf(false) }
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
                            imageVector = Icons.Outlined.Person,
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
                                        ), shape = MaterialTheme.shapes.medium
                                    )
                                    .fillMaxWidth()
                                    .height((screenSize.height / 4).dp)
                            )


                        }
                        DotsIndicator(
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .padding(horizontal = 8.dp)
                                .align(Alignment.BottomEnd),
                            totalDots = 5,
                            selectedIndex = pagerState.currentPage,
                            selectedColor = MaterialTheme.colorScheme.tertiary,
                            unSelectedColor = Color(0xFFC0C0C0)
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
                Column(Modifier.weight(1f)) {
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
                Column(
                    Modifier
                        .weight(1f)
                        .clickable {
                            nav.push(WishListScreen(screenSize))
                        }) {

                    Icon(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = stringResource(Res.string.app_name)
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
                        contentDescription = stringResource(Res.string.app_name)
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Profile",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = W200)
                    )
                }
            }

        }, floatingActionButton = {

            val addProductClicked = {
                nav.push(AddProductScreen(screenSize))
            }

            if (productGridState.isScrollInProgress)
                FloatingActionButton(
                    onClick = addProductClicked,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(Icons.Filled.Add, "Add Product")
                }
            else
                ExtendedFloatingActionButton(
                    onClick = addProductClicked,
                    icon = { Icon(Icons.Outlined.Add, "Add Product") },
                    text = { Text(text = "Add Product") },
                )

        }) { paddingValues ->

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
                                text = "Categories",
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
                                                "Category ${i + 1}",
                                                screenSize
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
                        text = "Latest Products",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = W700)
                    )


                    Text(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clickable {
                                nav.push(ProductsScreen(screenSize = screenSize))
                            },
                        text = "SEE All",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = W700)
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

                    items(10) { i ->
                        ProductUI(i)
                    }
                }


            }
        }
    }

    @Composable
    private fun ProductUI(index: Int) {
        val size = (screenSize.width / 2).dp
        val nav = LocalNavigator.currentOrThrow
        Column(modifier = Modifier.clickable {
            nav.push(ProductDetailScreen(index, screenSize))
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
                    contentDescription = stringResource(Res.string.search)
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