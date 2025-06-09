package onlytrade.app.ui.login.kyc

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
import onlytrade.app.ui.login.LoginScreen.Companion.validatePhoneNumber
import onlytrade.app.viewmodel.login.ui.KycViewModel
import onlytrade.app.viewmodel.login.ui.state.KycUiState.BlankEmailInputError
import onlytrade.app.viewmodel.login.ui.state.KycUiState.BlankMobileInputError
import onlytrade.app.viewmodel.login.ui.state.KycUiState.BlankNameError
import onlytrade.app.viewmodel.login.ui.state.KycUiState.DocsIncomplete
import onlytrade.app.viewmodel.login.ui.state.KycUiState.EmailFormatInputError
import onlytrade.app.viewmodel.login.ui.state.KycUiState.Idle
import onlytrade.app.viewmodel.login.ui.state.KycUiState.InReview
import onlytrade.app.viewmodel.login.ui.state.KycUiState.KycApiError
import onlytrade.app.viewmodel.login.ui.state.KycUiState.MobileNoFormatInputError
import onlytrade.app.viewmodel.login.ui.state.KycUiState.Uploading
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.cancel
import onlytrade.composeapp.generated.resources.kyc_1
import onlytrade.composeapp.generated.resources.kyc_2
import onlytrade.composeapp.generated.resources.kyc_3
import onlytrade.composeapp.generated.resources.kyc_4
import onlytrade.composeapp.generated.resources.outline_clear_24
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random

class KYCScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinViewModel<KycViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val nav = LocalNavigator.currentOrThrow
        val sharedCMP = LocalSharedCMP.current
        val productGridState = rememberLazyGridState()
        val headerVisible = productGridState.canScrollBackward.not()
        var showImagePicker by remember { mutableStateOf(false) }
        var choosePhotoId = false
        var photoId by remember {
            mutableStateOf<ByteArray?>(null)
        }
        var photo by remember {
            mutableStateOf<ByteArray?>(null)
        }
        var email by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var errorPhone by remember { mutableStateOf<String?>(null) }
        val inputEmailError = uiState is BlankEmailInputError || uiState is EmailFormatInputError

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
                            viewModel.uploadDocs(
                                name,
                                photoId,
                                photo,
                                email.ifBlank { null },
                                phone.ifBlank { null })
                        },
                        colors = ButtonDefaults.buttonColors(addProductColorScheme.submitProductBtn),
                        shape = MaterialTheme.shapes.extraSmall,
                        modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(Res.string.kyc_4),
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
                    value = name,
                    isError = uiState == BlankNameError,
                    onValueChange = {
                        name = it
                    },
                    shape = MaterialTheme.shapes.extraSmall,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        if (name.isNotBlank()) {
                            Icon(
                                painter = painterResource(Res.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null,
                                modifier = Modifier.clickable { name = "" })
                        }
                    },
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

                if (viewModel.isEmailProvided().not()) OutlinedTextField(
                    onValueChange = { email = it },
                    isError = inputEmailError,
                    shape = MaterialTheme.shapes.extraSmall,
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    trailingIcon = {
                        if (email.isNotBlank()) {
                            Icon(
                                painter = painterResource(Res.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null,
                                modifier = Modifier.clickable { email = "" })
                        }

                    },
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
                else OutlinedTextField(
                    value = phone,
                    isError = errorPhone != null,
                    onValueChange = {
                        phone = it
                        errorPhone = validatePhoneNumber(phone)
                    },
                    supportingText = {
                        errorPhone?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    shape = MaterialTheme.shapes.extraSmall,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        if (phone.isNotBlank()) {
                            Icon(
                                painter = painterResource(Res.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null,
                                modifier = Modifier.clickable { phone = "" })
                        }
                    },
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
                        docUI(sharedCMP, 0, photoId) {
                            choosePhotoId = true
                            showImagePicker = true
                        }
                    }

                    item {
                        docUI(sharedCMP, 1, photo) {
                            choosePhotoId = false
                            showImagePicker = true
                        }
                    }


                }

            }


            if (showImagePicker) {
                sharedCMP.GetImagesFromGallery {
                    showImagePicker = false
                    if (it.isEmpty())
                        return@GetImagesFromGallery

                    if (choosePhotoId) photoId = it.first()
                    else photo = it.first()
                }

            }


            when (uiState) {
                Uploading -> {
                    ShowToast("Uploading Docs...")
                }

                BlankNameError -> {
                    ShowToast("Name Required")
                    viewModel.idle()
                }

                BlankEmailInputError -> {
                    ShowToast("BlankEmail")
                    viewModel.idle()
                }

                BlankMobileInputError -> {
                    ShowToast("BlankMobileNo")
                    viewModel.idle()
                }

                DocsIncomplete -> {
                    ShowToast("Docs required.")
                    viewModel.idle()
                }

                EmailFormatInputError -> {
                    ShowToast("EmailFormatInputError")
                    viewModel.idle()
                }

                Idle -> {} // do nothing.
                InReview -> {
                    ShowToast("Added for review.")
                    LaunchedEffect(Unit) {
                        nav.pop()
                    }
                }

                is KycApiError -> {
                    ShowToast((uiState as KycApiError).error)
                    viewModel.idle()
                }

                MobileNoFormatInputError -> {
                    ShowToast("Wrong format for Mobile No")
                    viewModel.idle()
                }
            }
        }
    }


    @Composable
    private fun docUI(
        sharedCMP: SharedCMP,
        index: Int,
        byteArray: ByteArray? = null,
        onClick: () -> Unit
    ) {
        //  val nav = LocalNavigator.currentOrThrow
        Column(modifier = Modifier.clickable { onClick() }) {
            AsyncImage(
                model = byteArray,
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