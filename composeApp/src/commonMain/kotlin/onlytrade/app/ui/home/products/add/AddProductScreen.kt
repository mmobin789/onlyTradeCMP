package onlytrade.app.ui.home.products.add

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import onlytrade.app.ui.design.components.LocalSharedCMP
import onlytrade.app.ui.design.components.SharedCMP
import onlytrade.app.ui.design.components.ShowToast
import onlytrade.app.ui.design.components.isValidPrice
import onlytrade.app.ui.home.products.add.colorScheme.addProductColorScheme
import onlytrade.app.viewmodel.category.repository.data.db.Category
import onlytrade.app.viewmodel.category.sub.repository.data.db.Subcategory
import onlytrade.app.viewmodel.product.ui.AddProductViewModel
import onlytrade.app.viewmodel.product.ui.state.AddProductUiState.AddProductFailed
import onlytrade.app.viewmodel.product.ui.state.AddProductUiState.CategoryNotSelected
import onlytrade.app.viewmodel.product.ui.state.AddProductUiState.DescriptionBlank
import onlytrade.app.viewmodel.product.ui.state.AddProductUiState.EstPriceBlank
import onlytrade.app.viewmodel.product.ui.state.AddProductUiState.EstPriceLow
import onlytrade.app.viewmodel.product.ui.state.AddProductUiState.Idle
import onlytrade.app.viewmodel.product.ui.state.AddProductUiState.ImagesNotSelected
import onlytrade.app.viewmodel.product.ui.state.AddProductUiState.LessImagesSelected
import onlytrade.app.viewmodel.product.ui.state.AddProductUiState.Loading
import onlytrade.app.viewmodel.product.ui.state.AddProductUiState.MoreImagesSelected
import onlytrade.app.viewmodel.product.ui.state.AddProductUiState.ProductInReview
import onlytrade.app.viewmodel.product.ui.state.AddProductUiState.SubcategoryNotSelected
import onlytrade.app.viewmodel.product.ui.state.AddProductUiState.TitleBlank
import onlytrade.composeapp.generated.resources.Res
import onlytrade.composeapp.generated.resources.addProduct_1
import onlytrade.composeapp.generated.resources.addProduct_2
import onlytrade.composeapp.generated.resources.addProduct_3
import onlytrade.composeapp.generated.resources.addProduct_4
import onlytrade.composeapp.generated.resources.addProduct_5
import onlytrade.composeapp.generated.resources.addProduct_6
import onlytrade.composeapp.generated.resources.addProduct_7
import onlytrade.composeapp.generated.resources.addProduct_8
import onlytrade.composeapp.generated.resources.cancel
import onlytrade.composeapp.generated.resources.outline_clear_24
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random

class AddProductScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = koinViewModel<AddProductViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val nav = LocalNavigator.currentOrThrow
        val sharedCMP = LocalSharedCMP.current
        val productGridState = rememberLazyGridState()
        var title by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var selectedSubCategory by remember {
            mutableStateOf(
                Subcategory(
                    categoryId = -1,
                    id = -1,
                    name = ""
                )
            )
        }
        var selectedCategory by remember {
            mutableStateOf(
                Category(
                    -1, -1,
                    name = ""
                )
            )
        }
        var estPrice by remember { mutableStateOf("") }
        val headerVisible = productGridState.canScrollBackward.not()
        var showImagePicker by remember { mutableStateOf(false) }
        var toastMsg by remember { mutableStateOf("") }
        var galleryImages by remember {
            mutableStateOf(listOf<ByteArray>())
        }

        Scaffold(
            topBar = {
                AnimatedVisibility(visible = headerVisible) {
                    Column {
                        Row(
                            modifier = Modifier
                                .background(addProductColorScheme.topBarBG)
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp).padding(top = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.clickable { nav.pop() },
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = stringResource(Res.string.cancel)
                            )

                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = stringResource(Res.string.addProduct_1),
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
            },
            bottomBar = {

                Column(modifier = Modifier.background(addProductColorScheme.screenBG))
                {

                    Button(
                        onClick = { showImagePicker = true },
                        colors = ButtonDefaults.buttonColors(addProductColorScheme.submitProductBtn),
                        shape = MaterialTheme.shapes.extraSmall,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(Res.string.addProduct_2),
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.addProduct(
                                title = title,
                                categoryId = selectedCategory.id,
                                subcategoryId = selectedSubCategory.id,
                                description = description,
                                estPrice = estPrice,
                                images = galleryImages
                            )
                        },
                        colors = ButtonDefaults.buttonColors(addProductColorScheme.submitProductBtn),
                        shape = MaterialTheme.shapes.extraSmall,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(Res.string.addProduct_3),
                        )
                    }

                }

            },

            ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(addProductColorScheme.screenBG)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val categories = listOf(
                    "Consumer Electronic",
                    "Electronic Gadget",
                    "Furniture",
                    "Home Appliance",
                    "Household Furniture",
                    "Media and Entertainment"
                )
                val subcategories = listOf(
                    "Phone",
                    "Laptop",
                    "Computer",
                    "Home Appliance",
                    "Speakers",
                    "GPU",
                    "CPU",
                    "Headphone",
                    "Tablet",
                    "Monitor",
                    "TV"
                )
                var expandedCat by remember { mutableStateOf(false) }
                var expandedSubCat by remember { mutableStateOf(false) }


                OutlinedTextField(
                    isError = uiState is TitleBlank,
                    shape = MaterialTheme.shapes.extraSmall,
                    textStyle = TextStyle(fontSize = 15.sp),
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = title,
                    trailingIcon = {
                        if (title.isNotBlank()) {
                            Icon(
                                painter = painterResource(Res.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null,
                                modifier = Modifier.clickable { title = "" })
                        }
                    },
                    label = {
                        Text(
                            modifier = Modifier,
                            text = stringResource(Res.string.addProduct_4),
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = W500),
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    onValueChange = { title = it },
                )

                ExposedDropdownMenuBox(
                    expanded = expandedCat,
                    onExpandedChange = { expandedCat = !expandedCat }
                ) {
                    OutlinedTextField(
                        value = selectedCategory.name,
                        onValueChange = {},
                        shape = MaterialTheme.shapes.extraSmall,
                        textStyle = TextStyle(fontSize = 15.sp),
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true)
                            .fillMaxWidth(),
                        label = {
                            Text(
                                stringResource(Res.string.addProduct_5),
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = W500)
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCat)
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = expandedCat,
                        containerColor = addProductColorScheme.screenBG,
                        onDismissRequest = { expandedCat = false }
                    ) {
                        categories.forEachIndexed { index, category ->
                            DropdownMenuItem(
                                text = { Text(text = category) },
                                onClick = {
                                    selectedCategory = selectedCategory.copy(
                                        id = index.toLong(), name = category,
                                    )

                                    selectedSubCategory =
                                        selectedSubCategory.copy(categoryId = selectedCategory.id)
                                    expandedCat = false
                                }
                            )
                            if (index < categories.lastIndex) {
                                HorizontalDivider()
                            }
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = expandedSubCat,
                    onExpandedChange = { expandedSubCat = !expandedSubCat }
                ) {
                    OutlinedTextField(
                        value = selectedSubCategory.name,
                        onValueChange = {},
                        shape = MaterialTheme.shapes.extraSmall,
                        textStyle = TextStyle(fontSize = 15.sp),
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true)
                            .fillMaxWidth(),
                        label = {
                            Text(
                                stringResource(Res.string.addProduct_6),
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = W500)
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSubCat)
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = expandedSubCat,
                        containerColor = addProductColorScheme.screenBG,
                        onDismissRequest = { expandedSubCat = false }
                    ) {
                        subcategories.forEachIndexed { index, subcategory ->
                            DropdownMenuItem(
                                text = { Text(text = subcategory) },
                                onClick = {
                                    selectedSubCategory =
                                        selectedSubCategory.copy(
                                            name = subcategory,
                                            id = index.toLong()
                                        )
                                    expandedSubCat = false
                                }
                            )
                            if (index < subcategories.lastIndex) {
                                HorizontalDivider()
                            }
                        }
                    }
                }

                OutlinedTextField(
                    isError = uiState is DescriptionBlank,
                    shape = MaterialTheme.shapes.extraSmall,
                    textStyle = TextStyle(fontSize = 15.sp),
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = description,
                    trailingIcon = {
                        if (description.isNotBlank()) {
                            Icon(
                                painter = painterResource(Res.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null,
                                modifier = Modifier.clickable { description = "" })
                        }
                    },
                    label = {
                        Text(
                            modifier = Modifier,
                            text = stringResource(Res.string.addProduct_7),
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = W500),
                        )
                    },
                    minLines = 3,
                    maxLines = 3,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Default
                    ),
                    onValueChange = { description = it },
                )

                OutlinedTextField(
                    isError = uiState is EstPriceBlank || uiState is EstPriceLow,
                    shape = MaterialTheme.shapes.extraSmall,
                    textStyle = TextStyle(fontSize = 15.sp),
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = estPrice,
                    trailingIcon = {
                        if (estPrice.isNotBlank()) {
                            Icon(
                                painter = painterResource(Res.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null,
                                modifier = Modifier.clickable { estPrice = "" })
                        }
                    },
                    label = {
                        Text(
                            modifier = Modifier,
                            text = stringResource(Res.string.addProduct_8),
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = W500),
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    onValueChange = { input ->
                        if (input.isValidPrice()) {
                            estPrice = input
                        }
                    }
                )


                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.border(
                            color = addProductColorScheme.lazyVerticalGridBorder,
                            width = 1.dp,
                            shape = MaterialTheme.shapes.extraSmall
                        ).padding(16.dp)
                    ) {
                        if (galleryImages.isEmpty())
                            items(6) {
                                ProductUI(sharedCMP)
                            }
                        else items(galleryImages) {
                            ProductUI(sharedCMP, it)
                        }
                    }

                }

            }

        }
        if (showImagePicker) {
            sharedCMP.GetImagesFromGallery {
                showImagePicker = false
                galleryImages = it
            }

        }

        if (toastMsg.isNotBlank()) {
            ShowToast(toastMsg)
            if (uiState == ProductInReview) LaunchedEffect(Unit) {
                nav.pop()
            }
            toastMsg = ""
            viewModel.idle()
        }

        when (uiState) {
            Loading -> {
                toastMsg = "Adding Product"
            }

            ProductInReview -> {
                toastMsg = "Product Added for review."
            }

            is AddProductFailed -> {
                toastMsg = (uiState as AddProductFailed).error
            }

            Idle -> { //do nothing.
            }

            TitleBlank -> {
                toastMsg = "Product title is required."

            }

            DescriptionBlank -> {
                toastMsg = "Product Description is required."

            }

            EstPriceBlank -> {
                toastMsg = "Price estimate is required."
            }

            EstPriceLow -> {
                toastMsg = "Price estimate is too low."
            }

            ImagesNotSelected -> {
                toastMsg = "Product Images are required."
            }

            LessImagesSelected -> {
                toastMsg =
                    "Please add at least 4 images."
            }

            MoreImagesSelected -> {
                toastMsg = "Maximum images allowed are 6."
            }

            CategoryNotSelected -> {
                toastMsg = "Category is required."
            }

            SubcategoryNotSelected -> {
                toastMsg = "Subcategory is required."
            }

        }

    }

    @Composable
    private fun ProductUI(sharedCMP: SharedCMP, byteArray: ByteArray? = null) {
        val size = (sharedCMP.screenWidth / 4).dp
        //  val nav = LocalNavigator.currentOrThrow
        Column {
            AsyncImage(
                model = byteArray,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(size).clip(MaterialTheme.shapes.small)
                    .background(
                        color = Color(
                            Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                        ), shape = MaterialTheme.shapes.small
                    )
            )

        }
    }


}