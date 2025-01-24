package onlytrade.app.ui.login.newPassword

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import coil3.compose.AsyncImage
import onlytrade.app.ui.design.components.OTOutlinedTextField
import onlytrade.app.ui.design.components.PrimaryButton
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.app_logo
import onlytrade.composeapp.generated.resources.confirm_password
import onlytrade.composeapp.generated.resources.ic_quickmart_intro
import onlytrade.composeapp.generated.resources.ic_quickmart_intro_dark
import onlytrade.composeapp.generated.resources.new_password
import onlytrade.composeapp.generated.resources.password
import onlytrade.composeapp.generated.resources.pwd_visibility_24
import onlytrade.composeapp.generated.resources.pwd_visibility_off_24
import onlytrade.composeapp.generated.resources.save
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

class NewPasswordScreen : Screen {

    @Composable
    override fun Content() {
        var password by remember { mutableStateOf(TextFieldValue()) }
        var confirmPassword by remember { mutableStateOf(TextFieldValue()) }
        var passwordVisible by remember { mutableStateOf(true) }
        var confirmPasswordVisible by remember { mutableStateOf(true) }
        val inputWrongError = false //todo condition from vm.

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = if (isSystemInDarkTheme()) Res.drawable.ic_quickmart_intro_dark else Res.drawable.ic_quickmart_intro,
                contentScale = ContentScale.None,
                contentDescription = stringResource(Res.string.app_logo)
            )

            Text(
                text = stringResource(Res.string.new_password),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.W700,
                ),
            )

            Text(
                text = "Enter your new password",
                style = MaterialTheme.typography.bodyMedium
            )

            OTOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(Res.string.password),
                isError = false, //todo
                trailingIcon = {
                    if (password.text.isNotBlank()) {
                        val eyeIcon = if (passwordVisible) {
                            vectorResource(Res.drawable.pwd_visibility_24)
                        } else {
                            vectorResource(Res.drawable.pwd_visibility_off_24)
                        }
                        Icon(
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .clickable { passwordVisible = passwordVisible.not() },
                            imageVector = eyeIcon,
                            tint = MaterialTheme.colorScheme.secondary,
                            contentDescription = null
                        )
                    } else if (inputWrongError) {
                        Icon(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            imageVector = Icons.Outlined.Warning, //todo
                            tint = Color.Unspecified,
                            contentDescription = null
                        )
                    }
                },
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                modifier = Modifier
                    .fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )

            OTOutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = stringResource(Res.string.confirm_password),
                isError = false, //todo
                trailingIcon = {
                    if (confirmPassword.text.isNotBlank()) {
                        val eyeIcon = if (confirmPasswordVisible) {
                            vectorResource(Res.drawable.pwd_visibility_24)
                        } else {
                            vectorResource(Res.drawable.pwd_visibility_off_24)
                        }
                        Icon(
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .clickable {
                                    confirmPasswordVisible = confirmPasswordVisible.not()
                                },
                            imageVector = eyeIcon,
                            tint = MaterialTheme.colorScheme.secondary,
                            contentDescription = null
                        )
                    } else if (inputWrongError) {
                        Icon(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            imageVector = Icons.Outlined.Warning, //todo
                            tint = Color.Unspecified,
                            contentDescription = null
                        )
                    }
                },
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                modifier = Modifier
                    .fillMaxWidth(),
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )
            PrimaryButton(
                text = stringResource(Res.string.save),
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
            )

        }
    }
}