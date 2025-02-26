package onlytrade.app.ui.design.components

import androidx.compose.runtime.Composable

@Composable
fun ShowToast(text: String) = getToast().showToast(text)
fun String.isValidPrice() = (contains(".").not() && length < 8)
