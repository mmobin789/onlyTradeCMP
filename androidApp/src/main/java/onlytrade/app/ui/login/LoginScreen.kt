package onlytrade.app.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import onlytrade.app.ui.login.forgotPassword.ForgotPassword
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
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier.padding(16.dp),
                model = drawableRes(
                    R.drawable.quickmart_light_intro, R.drawable.quickmart_dark_intro
                ),
                contentScale = ContentScale.None,
                contentDescription = stringResource(R.string.app_logo)
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
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
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                        errorBorderColor = Color(0xFFEE4D4D),
                        focusedTextColor = MaterialTheme.colorScheme.secondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                        focusedLabelColor = MaterialTheme.colorScheme.secondary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
                        cursorColor = MaterialTheme.colorScheme.secondary
                    ),
                    isError = false,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    value = email,
                    trailingIcon = {
                        if (email.text.isNotBlank()) {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = 24.dp)
                                    .clickable {
                                        email = TextFieldValue("")
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
                    label = {
                        Text(
                            modifier = Modifier,
                            text = "Email",
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    onValueChange = {
                        email = it

                    }
                )
            if (orLabelVisible)
                Text(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "OR",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )

            if (email.text.isBlank())
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                        errorBorderColor = Color(0xFFEE4D4D),
                        focusedTextColor = MaterialTheme.colorScheme.secondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                        focusedLabelColor = MaterialTheme.colorScheme.secondary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
                        cursorColor = MaterialTheme.colorScheme.secondary
                    ),
                    isError = false, //todo
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .padding(top = if (orLabelVisible) 16.dp else 24.dp)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    value = phone,
                    trailingIcon = {
                        if (phone.text.isNotBlank()) {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = 24.dp)
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
                    label = {
                        Text(
                            modifier = Modifier,
                            text = "Mobile Number",
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    onValueChange = {
                        phone = it

                    }
                )

            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                    errorBorderColor = Color(0xFFEE4D4D),
                    focusedTextColor = MaterialTheme.colorScheme.secondary,
                    unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                    focusedLabelColor = MaterialTheme.colorScheme.secondary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
                    cursorColor = MaterialTheme.colorScheme.secondary
                ),
                isError = false, //todo
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                value = password,
                label = {
                    Text(
                        text = stringResource(R.string.password),
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                onValueChange = {
                    password = it

                }
            )


            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.End)
                    .clickable {
                        nav.push(ForgotPassword())
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