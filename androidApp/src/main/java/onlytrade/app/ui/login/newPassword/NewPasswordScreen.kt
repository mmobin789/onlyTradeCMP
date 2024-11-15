package onlytrade.app.ui.login.newPassword

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import coil3.compose.AsyncImage
import onlytrade.app.android.R
import onlytrade.app.ui.onboarding.color
import onlytrade.app.ui.onboarding.drawableRes

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
                text = stringResource(R.string.new_password),
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = "Enter your new password",
                color = color(0xFF6F7384, 0xFFA2A2A6),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )

            OutlinedTextField(colors = OutlinedTextFieldDefaults.colors(
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
                    .padding(16.dp)
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
                            modifier = Modifier.padding(horizontal = 24.dp),
                            imageVector = Icons.Outlined.Warning, //todo
                            tint = Color.Unspecified,
                            contentDescription = null
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                ),
                onValueChange = {
                    password = it

                })

            OutlinedTextField(colors = OutlinedTextFieldDefaults.colors(
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
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                value = confirmPassword,
                label = {
                    Text(
                        text = stringResource(R.string.confirm_password),
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                singleLine = true,
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    if (confirmPassword.text.isNotBlank()) {
                        val eyeIcon = if (confirmPasswordVisible) {
                            ImageVector.vectorResource(R.drawable.pwd_visibility_24)

                        } else {
                            ImageVector.vectorResource(R.drawable.pwd_visibility_off_24)
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
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                ),
                onValueChange = {
                    confirmPassword = it

                })

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                onClick = { }, //todo display pop up on new pwd set.
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = color(
                        MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.tertiary
                    )
                )
            ) {
                Text(
                    stringResource(R.string.save),
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

        }
    }
}