package onlytrade.app.ui.admin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import onlytrade.app.ui.design.components.LocalSharedCMP
import onlytrade.app.ui.design.components.SharedCMP
import onlytrade.app.ui.design.components.ShowToast
import onlytrade.app.ui.home.products.add.colorScheme.addProductColorScheme
import onlytrade.app.viewmodel.admin.UserDetailViewModel
import onlytrade.app.viewmodel.admin.ui.UserDetailUiState.Idle
import onlytrade.app.viewmodel.admin.ui.UserDetailUiState.UserNotFound
import onlytrade.app.viewmodel.admin.ui.UserDetailUiState.UserVerified
import onlytrade.app.viewmodel.admin.ui.UserDetailUiState.VerifyUserApiError
import onlytrade.app.viewmodel.admin.ui.UserDetailUiState.VerifyingUser
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.cancel
import onlytrade.composeapp.generated.resources.kyc_1
import onlytrade.composeapp.generated.resources.kyc_2
import onlytrade.composeapp.generated.resources.kyc_3
import onlytrade.composeapp.generated.resources.userDetail_1
import onlytrade.composeapp.generated.resources.userDetail_2
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random

class UserDetailScreen(private val userId: Long) : Screen {
    @Composable
    override fun Content() {
        val user = UserCache.get(userId)!!
        val viewModel = koinViewModel<UserDetailViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val nav = LocalNavigator.currentOrThrow
        val sharedCMP = LocalSharedCMP.current
        val productGridState = rememberLazyGridState()
        val headerVisible = productGridState.canScrollBackward.not()

        Scaffold(
            topBar = {
                AnimatedVisibility(visible = headerVisible) {
                    Column {
                        Row(
                            modifier = Modifier.background(addProductColorScheme.topBarBG)
                                .fillMaxWidth().padding(horizontal = 16.dp).padding(top = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.clickable { nav.pop() },
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = stringResource(Res.string.cancel)
                            )

                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = stringResource(Res.string.kyc_1),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = W700)
                            )
                        }

                        Spacer(
                            modifier = Modifier.background(addProductColorScheme.topBarBG)
                                .height(1.dp).fillMaxWidth()
                        )

                    }
                }
            },
            bottomBar = {

                Column(modifier = Modifier.background(addProductColorScheme.screenBG)) {
                    Button(
                        onClick = {
                            when (uiState) {
                                VerifyingUser -> {}
                                else -> viewModel.verifyUser(userId)

                            }
                        },
                        colors = ButtonDefaults.buttonColors(addProductColorScheme.submitProductBtn),
                        shape = MaterialTheme.shapes.extraSmall,
                        modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(if (uiState == VerifyingUser) Res.string.userDetail_2 else Res.string.userDetail_1),
                        )
                    }

                }

            },

            ) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
                    .background(addProductColorScheme.screenBG).fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = stringResource(Res.string.kyc_2),
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = W500),
                )

                OutlinedTextField(
                    readOnly = true,
                    onValueChange = { //do nothing
                    },
                    value = user.name!!,
                    shape = MaterialTheme.shapes.extraSmall,
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            modifier = Modifier,
                            text = stringResource(Res.string.kyc_3),
                            style = MaterialTheme.typography.bodySmall,
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                    ),
                )

                OutlinedTextField(
                    readOnly = true,
                    onValueChange = { },
                    shape = MaterialTheme.shapes.extraSmall,
                    modifier = Modifier.fillMaxWidth(),
                    value = user.email!!,
                    label = {
                        Text(
                            modifier = Modifier,
                            text = "Email",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email, imeAction = ImeAction.Done
                    ),
                )

                OutlinedTextField(
                    readOnly = true,
                    value = user.phone!!,
                    onValueChange = {},
                    shape = MaterialTheme.shapes.extraSmall,
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            modifier = Modifier,
                            text = "Mobile Number",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done
                    ),
                )


                LazyVerticalGrid(
                    columns = GridCells.Adaptive(sharedCMP.screenWidth / 2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 8.dp).border(
                        color = addProductColorScheme.lazyVerticalGridBorder,
                        width = 1.dp,
                        shape = MaterialTheme.shapes.extraSmall
                    ).padding(16.dp)
                ) {
                    item {
                        docUI(sharedCMP, 0, user.photo)
                    }

                    item {
                        docUI(sharedCMP, 1, user.photoId)
                    }


                }

            }


            when (uiState) {
                Idle -> {
                    //do nothing.
                }

                UserNotFound -> {
                    ShowToast("User not found.")
                }

                UserVerified -> TODO()
                is VerifyUserApiError -> TODO()
                VerifyingUser -> TODO()
            }
        }
    }


    @Composable
    private fun docUI(
        sharedCMP: SharedCMP,
        index: Int,
        imageUrl: String?
    ) {
        //  val nav = LocalNavigator.currentOrThrow
        Column {
            AsyncImage(
                model = imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.width(sharedCMP.screenWidth)
                    .height(if (index == 0) sharedCMP.screenWidth else sharedCMP.screenWidth / 2)
                    .clip(MaterialTheme.shapes.small).background(
                        color = Color(
                            Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                        ), shape = MaterialTheme.shapes.small
                    )
            )

        }
    }


}