package onlytrade.app.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
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
        val inputWrongError = false //todo condition from vm.

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier.padding(16.dp),
                model = drawableRes(
                    R.drawable.ic_quickmart_intro, R.drawable.ic_quickmart_intro
                ),
                contentScale = ContentScale.None,
                contentDescription = stringResource(R.string.app_logo)
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = "Login",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = "Enter your email or mobile number",
                color = color(0xFF6F7384, 0xFFA2A2A6),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )


            if (phone.text.isBlank())
                OTOutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    isError = false,
                    trailingIcon = {
                        if (email.text.isNotBlank()) {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = 24.dp)
                                    .clickable {
                                        email = TextFieldValue("")
                                    },
                                painter = painterResource(R.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null
                            )
                        } else if (inputWrongError) {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = 24.dp),
                                imageVector = ImageVector.vectorResource(R.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null
                            )
                        }
                    },
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )
            if (orLabelVisible)
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = "OR",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )

            if (email.text.isBlank())
                OTOutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = "Mobile Number",
                    isError = false, //todo
                    trailingIcon = {
                        if (phone.text.isNotBlank()) {
                            Icon(
                                modifier = Modifier
                                    .clickable {
                                        phone = TextFieldValue("")
                                    },
                                imageVector = ImageVector.vectorResource(R.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null
                            )
                        } else if (inputWrongError) {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = 24.dp),
                                imageVector = ImageVector.vectorResource(R.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null
                            )
                        }
                    },
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )


            OTOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(R.string.password),
                isError = false, //todo
                trailingIcon = {
                    if (password.text.isNotBlank()) {
                        val eyeIcon = if (passwordVisible) {
                            ImageVector.vectorResource(R.drawable.pwd_visibility_24)
                        } else {
                            ImageVector.vectorResource(R.drawable.pwd_visibility_off_24)
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
                            modifier = Modifier
                                .padding(horizontal = 24.dp),
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

            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.End)
                    .clickable {
                        nav.push(ForgotPasswordScreen())
                    },
                text = stringResource(R.string.forgot_pwd),
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.tertiary
                )
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { }, //todo
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = color(
                        MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.tertiary
                    )
                )
            ) {
                Text(
                    stringResource(R.string.login),
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

        }
    }


}