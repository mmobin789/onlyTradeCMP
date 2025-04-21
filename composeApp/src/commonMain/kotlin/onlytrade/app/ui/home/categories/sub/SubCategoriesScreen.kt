package onlytrade.app.ui.home.categories.sub

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W200
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import onlytrade.app.ui.design.components.LocalSharedCMP
import onlytrade.app.ui.design.components.SharedCMP
import onlytrade.app.ui.home.HomeScreen
import onlytrade.app.ui.home.categories.sub.colorScheme.subCategoriesColorScheme
import onlytrade.app.ui.home.products.ProductsScreen
import onlytrade.app.ui.home.products.my.MyProductsScreen
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_name
import onlytrade.composeapp.generated.resources.cancel
import onlytrade.composeapp.generated.resources.outline_compare_arrows_24
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import kotlin.random.Random

class SubCategoriesScreen(private val categoryName: String) :
    Screen {

    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val sharedCMP = LocalSharedCMP.current
        val productGridState = rememberLazyGridState()
        val headerVisible = productGridState.canScrollBackward.not()

        Scaffold(
            topBar = {
                AnimatedVisibility(visible = headerVisible) {
                    Column {
                        Row(
                            modifier = Modifier
                                .background(subCategoriesColorScheme.categoryBarBG)
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {


                            Icon(
                                modifier = Modifier.clickable { nav.pop() },
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = stringResource(Res.string.cancel)
                            )

                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = categoryName,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = W700)
                            )


                        }

                        Spacer(
                            modifier = Modifier
                                .background(subCategoriesColorScheme.categoryBarBG)
                                .height(1.dp)
                                .fillMaxWidth()
                        )

                    }
                }
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .background(subCategoriesColorScheme.botBarBG)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        Modifier
                            .weight(1f)
                            .clickable {
                                nav.replace(HomeScreen())
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
                    }
                    /*   Column(Modifier.weight(1f)) {
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
                                nav.push(MyProductsScreen())
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

            })
        { paddingValues ->

            LazyVerticalGrid(
                state = productGridState,
                modifier = Modifier
                    .background(subCategoriesColorScheme.screenBG)
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                columns = GridCells.Fixed(2)
            ) {

                items(10) { i ->
                    SubCategoryUI(sharedCMP, i)
                }
            }
        }


    }


    @Composable
    private fun SubCategoryUI(sharedCMP: SharedCMP, index: Int) {
        val size = (sharedCMP.screenWidth / 2).dp
        val nav = LocalNavigator.currentOrThrow
        Column(modifier = Modifier.clickable {
            nav.push(ProductsScreen("Subcategory ${index + 1}"))
        }) {
            Box(
                Modifier
                    .size(size)
                    .background(
                        color = Color(
                            Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                        ), shape = MaterialTheme.shapes.extraLarge
                    )
            )

            Text(
                text = "Subcategory ${index + 1}",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = W700)
            )


        }
    }
}