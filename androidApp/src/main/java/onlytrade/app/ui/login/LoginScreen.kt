package onlytrade.app.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import onlytrade.app.android.R
import onlytrade.app.ui.design.components.OTOutlinedTextField
import onlytrade.app.ui.design.components.PrimaryButton
import onlytrade.app.ui.design.components.ShowToast
import onlytrade.app.ui.home.HomeScreen
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
import org.koin.compose.viewmodel.koinViewModel

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val loginViewModel = koinViewModel<LoginViewModel>()
        val uiState by loginViewModel.uiState.collectAsState()
        val nav = LocalNavigator.currentOrThrow
        var email by remember { mutableStateOf(TextFieldValue()) }
        var phone by remember { mutableStateOf(TextFieldValue()) }
        var password by remember { mutableStateOf(TextFieldValue()) }
        var passwordVisible by remember { mutableStateOf(true) }

        val orLabelVisible = email.text.isBlank() && phone.text.isBlank()

        val inputMobileError =
            uiState is BlankMobileInputError || uiState is MobileNoFormatInputError
        val inputEmailError = uiState is BlankEmailInputError || uiState is EmailFormatInputError
        val inputPwdError = uiState is BlankPwdInputError || uiState is SmallPwdInputError


        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = R.drawable.ic_quickmart_intro,
                contentScale = ContentScale.None,
                contentDescription = stringResource(R.string.app_logo),
                modifier = Modifier
                    .padding(top = 32.dp)
                    .align(Alignment.Start)
            )

            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = W700)
            )

            Text(
                text = "Enter your email or mobile number",
                style = MaterialTheme.typography.labelLarge
            )

            if (phone.text.isBlank()) {
                OTOutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email", isError = inputEmailError,
                    trailingIcon = {
                        if (email.text.isNotBlank()) {
                            Icon(
                                painter = painterResource(R.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null,
                                modifier = Modifier.clickable { email = TextFieldValue("") }
                            )
                        }
                    },
                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                )
            }

            if (orLabelVisible) {
                Text(
                    text = "OR",
                    style = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Center),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (email.text.isBlank()) {
                OTOutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = "Mobile Number", isError = inputMobileError,
                    trailingIcon = {
                        if (phone.text.isNotBlank()) {
                            Icon(
                                painter = painterResource(R.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null,
                                modifier = Modifier.clickable { phone = TextFieldValue("") }
                            )
                        }
                    }, keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next
                )
            }

            OTOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(R.string.password),
                isError = inputPwdError,
                trailingIcon = {
                    if (password.text.isNotEmpty()) {
                        val eyeIcon = if (passwordVisible) {
                            ImageVector.vectorResource(R.drawable.pwd_visibility_24)
                        } else {
                            ImageVector.vectorResource(R.drawable.pwd_visibility_off_24)
                        }
                        Icon(imageVector = eyeIcon,
                            tint = MaterialTheme.colorScheme.secondary,
                            contentDescription = null,
                            modifier = Modifier.clickable { passwordVisible = !passwordVisible })
                    }
                },
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )

            Text(
                text = stringResource(R.string.forgot_pwd),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = W700),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { nav.push(ForgotPasswordScreen()) })

            PrimaryButton(
                text = stringResource(R.string.login),
                onClick = {
                    if (email.text.isBlank())
                        loginViewModel.doMobileLogin(
                            mobileNo = phone.text,
                            pwd = password.text
                        ) else loginViewModel.doEmailLogin(email = email.text, pwd = password.text)


                },
                modifier = Modifier
                    .fillMaxWidth()

            )

            when (uiState) {
                is LoggedIn -> nav.replaceAll(HomeScreen())
                is ApiError -> {
                    ShowToast("ApiError")
                }

                BlankEmailInputError -> {
                    ShowToast("BlankEmail")
                }

                BlankFormError -> {
                    ShowToast("BlankForm")
                    loginViewModel.idle()
                }

                BlankMobileInputError -> {
                    ShowToast("BlankMobileInputError")
                    loginViewModel.idle()
                }

                BlankPwdInputError -> {
                    ShowToast("BlankPwdInputError")
                    loginViewModel.idle()
                }

                EmailFormatInputError -> {
                    ShowToast("EmailFormatInputError")
                    loginViewModel.idle()
                }

                Idle -> {
                    // do nothing.
                }

                Loading -> {
                    ShowToast("Loading")
                    loginViewModel.idle()
                }

                MobileNoFormatInputError -> {
                    ShowToast("MobileNoFormatInputError")
                    loginViewModel.idle()
                }

                SmallPwdInputError -> {
                    ShowToast("SmallPwdInputError")
                    loginViewModel.idle()
                }
            }
        }
    }

}
