package onlytrade.app.ui.design.components
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun OTOutlinedTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    isError: Boolean = false,
    trailingIcon: @Composable() (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    style: TextStyle = MaterialTheme.typography.body1
) {
    OutlinedTextField(
        isError = isError,
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        trailingIcon = trailingIcon,
        label = {
            Text(
                modifier = Modifier,
                text = label,
                style = style
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        onValueChange = onValueChange,
        visualTransformation = visualTransformation
    )
}

@Composable
fun OTTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    isError: Boolean = false,
    trailingIcon: @Composable() (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        isError = isError,
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        trailingIcon = trailingIcon,
        label = {
            Text(
                modifier = Modifier,
                text = label,
                style = MaterialTheme.typography.body1
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        onValueChange = onValueChange,
        visualTransformation = visualTransformation
    )
}