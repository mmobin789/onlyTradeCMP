package onlytrade.app.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import onlytrade.app.android.R
import onlytrade.app.ui.design_system.components.OTOutlinedTextField
import onlytrade.app.ui.login.forgotPassword.ForgotPasswordScreen
import onlytrade.app.ui.onboarding.color
import onlytrade.app.ui.onboarding.drawableRes

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        var email by remember { mutableStateOf(TextFieldValue()) }
        var phone by remember { mutableStateOf(TextFieldValue()) }
        var password by remember { mutableStateOf(TextFieldValue()) }
        var passwordVisible by remember { mutableStateOf(true) }
        val orLabelVisible = email.text.isBlank() && phone.text.isBlank()
        val inputWrongError = false // TODO: condition from ViewModel

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            AsyncImage(
                model = drawableRes(
                    R.drawable.ic_quickmart_intro, R.drawable.ic_quickmart_intro
                ),
                contentScale = ContentScale.None,
                contentDescription = stringResource(R.string.app_logo),
                modifier = Modifier
                    .padding(top = 32.dp)
                    .align(Alignment.Start)
            )

            Text(
                text = "Login",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )

            Text(
                text = "Enter your email or mobile number",
                color = color(0xFF6F7384, 0xFFA2A2A6),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.align(Alignment.Start)
            )

            if (phone.text.isBlank()) {
                OTOutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email", isError = inputWrongError,
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
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            if (email.text.isBlank()) {
                OTOutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = "Mobile Number", isError = inputWrongError,
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
                isError = inputWrongError,
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
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.tertiary
                ),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { nav.push(ForgotPasswordScreen()) })

            Button(
                onClick = { /* TODO: Handle login */ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.login),
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}
