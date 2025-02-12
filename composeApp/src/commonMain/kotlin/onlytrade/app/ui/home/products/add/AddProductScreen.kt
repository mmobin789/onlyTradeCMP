package onlytrade.app.ui.home.products.add


import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import onlytrade.app.ui.design.components.ScreenSize
import onlytrade.app.ui.design.components.getToast
import onlytrade.app.ui.home.products.add.colorScheme.addProductColorScheme
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.cancel
import onlytrade.composeapp.generated.resources.outline_clear_24
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class AddProductScreen(private val screenSize: ScreenSize) : Screen {

    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val productGridState = rememberLazyGridState()
        val headerVisible = productGridState.canScrollBackward.not()

        Scaffold(topBar = {
            AnimatedVisibility(visible = headerVisible) {
                Column {
                    Row(
                        modifier = Modifier
                            .background(addProductColorScheme.topBarBG)
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {


                        Icon(
                            modifier = Modifier.clickable { nav.pop() },
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = stringResource(Res.string.cancel)
                        )

                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = "Add a New Trade Product",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = W700)
                        )


                    }

                    Spacer(
                        modifier = Modifier
                            .background(addProductColorScheme.topBarBG)
                            .height(1.dp)
                            .fillMaxWidth()
                    )

                }
            }
        }) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(addProductColorScheme.screenBG)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                var productTitle by remember { mutableStateOf(TextFieldValue()) }
                var productDesc by remember { mutableStateOf(TextFieldValue()) }
                val inputWrongError = false // TODO: condition from ViewModel
                Text(
                    text = "Product Name or Title",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = W500)
                )
                OutlinedTextField(
                    isError = inputWrongError,
                    shape = MaterialTheme.shapes.extraSmall,
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = productTitle,
                    trailingIcon = {
                        if (productTitle.text.isNotBlank()) {
                            Icon(
                                painter = painterResource(Res.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null,
                                modifier = Modifier.clickable { productTitle = TextFieldValue("") })
                        }
                    },
                    label = {
                        Text(
                            modifier = Modifier,
                            text = "Product Title",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = W500),
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    onValueChange = { productTitle = it },
                )

                Text(
                    text = "Product Details",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = W500)
                )

                OutlinedTextField(
                    isError = inputWrongError,
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = productDesc,
                    trailingIcon = {
                        if (productDesc.text.isNotBlank()) {
                            Icon(painter = painterResource(Res.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null,
                                modifier = Modifier.clickable { productDesc = TextFieldValue("") })
                        }
                    },
                    label = {
                        Text(
                            modifier = Modifier,
                            text = "Product Details",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = W500),
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    onValueChange = { productDesc = it },
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp),
                    text = "Add Product Pictures",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = W700)
                )
                val size = (screenSize.width / 4).dp

                Icon(
                    Icons.Filled.Add,
                    "Add Product",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            getToast().showToast("clicked")
                        }
                        .background(
                            shape = MaterialTheme.shapes.large,
                            color = addProductColorScheme.addProductBtn
                        )
                        .size(size)
                )
            }
        }


    }
}