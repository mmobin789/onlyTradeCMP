package onlytrade.app.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import onlytrade.app.ui.design.components.SharedCMP
import onlytrade.app.ui.design.components.ShowToast
import onlytrade.app.ui.home.HomeScreen
import onlytrade.app.ui.login.colorScheme.loginColorScheme
import onlytrade.app.ui.login.forgotPassword.ForgotPasswordScreen
import onlytrade.app.viewmodel.login.ui.LoginUiState.ApiError
import onlytrade.app.viewmodel.login.ui.LoginUiState.BlankEmailInputError
import onlytrade.app.viewmodel.login.ui.LoginUiState.BlankFormError
import onlytrade.app.viewmodel.login.ui.LoginUiState.BlankMobileInputError
import onlytrade.app.viewmodel.login.ui.LoginUiState.BlankPwdInputError
import onlytrade.app.viewmodel.login.ui.LoginUiState.EmailFormatInputError
import onlytrade.app.viewmodel.login.ui.LoginUiState.Idle
import onlytrade.app.viewmodel.login.ui.LoginUiState.Loading
import onlytrade.app.viewmodel.login.ui.LoginUiState.LoggedIn
import onlytrade.app.viewmodel.login.ui.LoginUiState.MobileNoFormatInputError
import onlytrade.app.viewmodel.login.ui.LoginUiState.SmallPwdInputError
import onlytrade.app.viewmodel.login.ui.LoginViewModel
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_logo
import onlytrade.composeapp.generated.resources.forgot_pwd
import onlytrade.composeapp.generated.resources.ic_quickmart_intro
import onlytrade.composeapp.generated.resources.ic_quickmart_intro_dark
import onlytrade.composeapp.generated.resources.outline_clear_24
import onlytrade.composeapp.generated.resources.password
import onlytrade.composeapp.generated.resources.pwd_visibility_24
import onlytrade.composeapp.generated.resources.pwd_visibility_off_24
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel

class LoginScreen(private val sharedCMP: SharedCMP) : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<LoginViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val nav = LocalNavigator.currentOrThrow
        var email by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf(value = "") }
        var password by remember { mutableStateOf(TextFieldValue()) }
        var passwordVisible by remember { mutableStateOf(true) }
        val orLabelVisible = email.isBlank() && phone.isBlank()

        var errorPhone by remember { mutableStateOf<String?>(null) }
        var errorPassword by remember { mutableStateOf<String?>(null) }

        val inputEmailError = uiState is BlankEmailInputError || uiState is EmailFormatInputError





        Column(
            modifier = Modifier
                .background(loginColorScheme.screenBG)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = if (isSystemInDarkTheme()) Res.drawable.ic_quickmart_intro_dark else Res.drawable.ic_quickmart_intro,
                contentScale = ContentScale.None,
                contentDescription = stringResource(Res.string.app_logo),
                modifier = Modifier
                    .padding(top = 32.dp)
                    .align(Alignment.Start)
            )

            Text(
                text = "Login",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = W700)
            )

            Text(
                text = "Enter your email or mobile number",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelLarge
            )

            if (phone.isBlank()) {
                OutlinedTextField(
                    onValueChange = { email = it },
                    isError = inputEmailError,
                    shape = MaterialTheme.shapes.extraSmall,
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = email,
                    trailingIcon = {
                        if (email.isNotBlank()) {
                            Icon(
                                painter = painterResource(Res.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null,
                                modifier = Modifier.clickable { email = "" }
                            )
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
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                )
            }

            if (orLabelVisible) {
                Text(
                    text = "OR",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Center),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (email.isBlank()) {
                OutlinedTextField(
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
                    modifier = Modifier
                        .fillMaxWidth(),
                    trailingIcon = {
                        if (phone.isNotBlank()) {
                            Icon(
                                painter = painterResource(Res.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null,
                                modifier = Modifier.clickable { phone = "" }
                            )
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
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                )
            }

            OutlinedTextField(
                isError = errorPassword != null,
                shape = MaterialTheme.shapes.extraSmall,
                modifier = Modifier
                    .fillMaxWidth(),
                value = password,
                onValueChange = {
                    password = it
                    errorPassword = validatePassword(password.text)
                },
                supportingText = {
                    errorPassword?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                trailingIcon = {
                    if (password.text.isNotEmpty()) {
                        val eyeIcon = if (passwordVisible) {
                            vectorResource(Res.drawable.pwd_visibility_24)
                        } else {
                            vectorResource(Res.drawable.pwd_visibility_off_24)
                        }
                        Icon(imageVector = eyeIcon,
                            tint = MaterialTheme.colorScheme.secondary,
                            contentDescription = null,
                            modifier = Modifier.clickable { passwordVisible = !passwordVisible })
                    }
                },
                label = {
                    Text(
                        modifier = Modifier,
                        text = stringResource(Res.string.password),
                        style = MaterialTheme.typography.bodySmall,
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )

            Text(
                text = stringResource(Res.string.forgot_pwd),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = W700),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { nav.push(ForgotPasswordScreen()) })

            Button(
                onClick = {
                    if (email.isBlank())
                        viewModel.doMobileLogin(
                            mobileNo = phone,
                            pwd = password.text
                        ) else viewModel.doEmailLogin(email = email, pwd = password.text)


                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(loginColorScheme.loginBtn),

            ){
                Text(text = "Login", color = MaterialTheme.colorScheme.onBackground)
            }

            when (uiState) {
                is LoggedIn -> nav.replaceAll(HomeScreen(sharedCMP))
                is ApiError -> {
                    ShowToast("ApiError")
                }

                BlankEmailInputError -> {
                    ShowToast("BlankEmail")
                }

                BlankFormError -> {
                    ShowToast("BlankForm")
                    viewModel.idle()
                }

                BlankMobileInputError -> {
                    ShowToast("BlankMobileInputError")
                    viewModel.idle()
                }

                BlankPwdInputError -> {
                    ShowToast("BlankPwdInputError")
                    viewModel.idle()
                }

                EmailFormatInputError -> {
                    ShowToast("EmailFormatInputError")
                    viewModel.idle()
                }

                Idle -> {
                    // do nothing.
                }

                Loading -> {
                    ShowToast("Loading")
                    viewModel.idle()
                }

                MobileNoFormatInputError -> {
                    ShowToast("MobileNoFormatInputError")
                    viewModel.idle()
                }

                SmallPwdInputError -> {
                    ShowToast("SmallPwdInputError")
                    viewModel.idle()
                }
            }
        }
    }

    private fun validatePhoneNumber(input: String): String? = when {
        input.isEmpty() -> "Phone number cannot be empty."
        !input.startsWith("92") && !input.startsWith("+92") && !input.startsWith("03") -> "Phone number must start with either '92', '+92' or '03'"
        input.startsWith("92") && input.length != 12 -> "Phone number must be 12 digits!"
        input.startsWith("+92") && input.length != 13 -> "Phone number must be 12 digits!"
        input.startsWith("03") && input.length != 11 -> "Phone number must be 11 digits!"
        else -> null
    }
    private fun validatePassword(input: String): String? = when {
        input.isEmpty() -> "Password cannot be empty."
        input.length < 7 -> "Password must be at least 7 characters long."
        else -> null

    }

}
