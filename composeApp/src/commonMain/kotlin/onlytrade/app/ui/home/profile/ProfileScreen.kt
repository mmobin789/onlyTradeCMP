package onlytrade.app.ui.home.profile

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.W200
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import onlytrade.app.ui.design.components.ShowToast
import onlytrade.app.ui.design.components.getToast
import onlytrade.app.ui.home.HomeScreen
import onlytrade.app.ui.home.products.my.MyProductsScreen
import onlytrade.app.ui.home.profile.colorScheme.profileColorScheme
import onlytrade.app.ui.login.LoginScreen
import onlytrade.app.ui.login.kyc.KYCScreen
import onlytrade.app.viewmodel.profile.ui.ProfileUiState.BlankNameError
import onlytrade.app.viewmodel.profile.ui.ProfileUiState.Idle
import onlytrade.app.viewmodel.profile.ui.ProfileUiState.InvalidEmailFormatError
import onlytrade.app.viewmodel.profile.ui.ProfileUiState.InvalidPhoneFormatError
import onlytrade.app.viewmodel.profile.ui.ProfileUiState.LoadingKycStatus
import onlytrade.app.viewmodel.profile.ui.ProfileUiState.LoggedOut
import onlytrade.app.viewmodel.profile.ui.ProfileUiState.UserDetailApiError
import onlytrade.app.viewmodel.profile.ui.ProfileUiState.VerifiedUser
import onlytrade.app.viewmodel.profile.ui.ProfileViewModel
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_name
import onlytrade.composeapp.generated.resources.botBar_1
import onlytrade.composeapp.generated.resources.botBar_2
import onlytrade.composeapp.generated.resources.botBar_3
import onlytrade.composeapp.generated.resources.botBar_4
import onlytrade.composeapp.generated.resources.cancel
import onlytrade.composeapp.generated.resources.home_5
import onlytrade.composeapp.generated.resources.outline_compare_arrows_24
import onlytrade.composeapp.generated.resources.profile_1
import onlytrade.composeapp.generated.resources.profile_4
import onlytrade.composeapp.generated.resources.profile_5
import onlytrade.composeapp.generated.resources.profile_6
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel

class ProfileScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val viewModel = koinViewModel<ProfileViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val nav = LocalNavigator.currentOrThrow
        var isEditing by remember { mutableStateOf(false) }
        var name = viewModel.user.name.orEmpty()
        var email = viewModel.user.email.orEmpty()
        var phone = viewModel.user.phone.orEmpty()
        val loggedInWithPhone = phone.isNotBlank()
        val verifiedUser = uiState == VerifiedUser


        Scaffold(topBar = {
            TopAppBar(
                title = { Text(text = stringResource(Res.string.profile_1)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = profileColorScheme.topBarBG
                ),
                navigationIcon = {
                    IconButton(onClick = { nav.pop() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = stringResource(Res.string.cancel)
                        )
                    }
                },
                actions = {
                    /*IconButton(onClick = { isEditing = !isEditing }) {
                        Icon(
                            imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                            contentDescription = if (isEditing) "Save" else "Edit"
                        )
                    }*/
                })
        }, bottomBar = {
            Row(
                modifier = Modifier.background(profileColorScheme.botBarBG).padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    Modifier.weight(1f).clickable { nav.replace(HomeScreen()) }) {
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
                }
                Column(Modifier.weight(1f)) {
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
                    Modifier.weight(1f).clickable { nav.push(MyProductsScreen()) }) {
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

                Column(Modifier.weight(1f)) {
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

        }) { paddingValues ->
            Column(
                modifier = Modifier.background(profileColorScheme.screenBG).padding(paddingValues)
                    .fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
            ) {
                /* Image(
                     imageVector = Icons.Default.AccountCircle,
                     contentDescription = "Profile Picture",
                     modifier = Modifier.size(90.dp)
                         .background(Color.Gray, shape = CircleShape)
                 )
                 Spacer(Modifier.height(8.dp))*/
                Text(
                    text = stringResource(Res.string.profile_6),
                    style = MaterialTheme.typography.titleLarge
                )

                if (isEditing) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Name"
                            )
                        })
                    if (loggedInWithPhone) {
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "Email Icon"
                                )
                            })
                    } else {
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text("Phone") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = "Phone Icon"
                                )
                            })
                    }
                } else {
                    if (phone.isNotBlank()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Phone,
                                contentDescription = "Phone Icon"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(phone)
                        }
                    }

                    if (email.isNotBlank()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Email,
                                contentDescription = "Email Icon"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(email)
                        }
                    }
                }

                if (!isEditing) {
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        /*   OutlinedButton(
                               modifier = Modifier.weight(1f),
                               onClick = { *//* TODO *//* },
                                    shape = MaterialTheme.shapes.small,
                                    border = BorderStroke(1.dp, profileColorScheme.activeTradesBtn),
                                ) {
                                    Text(
                                        text = stringResource(Res.string.profile_5),
                                        color = productDetailColorScheme.offerTradeBtnText
                                    )
                                }*/
                        if (verifiedUser.not())
                            Button(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    when (uiState) {
                                        LoadingKycStatus -> {} // do nothing.
                                        else -> nav.push(KYCScreen())
                                    }
                                },
                                shape = MaterialTheme.shapes.small,
                                colors = ButtonDefaults.buttonColors(profileColorScheme.myOffersBtn)
                            ) {
                                Text(text = stringResource(if (uiState == LoadingKycStatus) Res.string.home_5 else Res.string.profile_5))
                            }
                    }

                    Button(
                        onClick = { viewModel.logOut() },
                        colors = ButtonDefaults.buttonColors(profileColorScheme.logoutBtn),
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(Res.string.profile_4))
                        Spacer(Modifier.width(8.dp))
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                when (uiState) {
                    Idle -> {
                        //do nothing
                    }

                    is UserDetailApiError -> ShowToast((uiState as UserDetailApiError).error)

                    BlankNameError -> {
                        ShowToast("BlankNameError")
                        viewModel.idle()
                    }

                    InvalidPhoneFormatError -> {
                        ShowToast("InvalidPhoneFormatError")
                        viewModel.idle()
                    }

                    InvalidEmailFormatError -> {
                        ShowToast("InvalidEmailFormatError")
                        viewModel.idle()
                    }

                    LoggedOut -> {
                        getToast().showToast("Logged Out Successfully")
                        nav.replaceAll(LoginScreen())
                    }


                    VerifiedUser -> {} // do something if verified user
                    else -> {}
                }

            }
        }
    }
}
