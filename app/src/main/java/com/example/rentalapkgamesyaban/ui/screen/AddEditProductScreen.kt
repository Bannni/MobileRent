package com.example.rentalapkgamesyaban.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rentalapkgamesyaban.ui.viewmodel.ProductViewModel

@Composable
fun AddEditProductScreen(
    productViewModel: ProductViewModel,
    productId: Int?,
    onNavigateBack: () -> Unit
) {
    val products by productViewModel.products.collectAsState()
    val editingProduct = products.find { it.id == productId }

    var name by remember { mutableStateOf("") }
    var platform by remember { mutableStateOf("game") }
    var description by remember { mutableStateOf("") }
    var priceStr by remember { mutableStateOf("") }
    var externalLink by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf("") }

    LaunchedEffect(editingProduct) {
        editingProduct?.let {
            name = it.name
            platform = it.platform
            description = it.description
            priceStr = it.price.toString()
            externalLink = it.externalLink
            imageUri = it.imageUri
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri = it.toString() }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(if (productId == null) "Add Product" else "Edit Product", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        
        Row {
            RadioButton(selected = platform == "game", onClick = { platform = "game" })
            Text("Game", modifier = Modifier.padding(top = 12.dp))
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(selected = platform == "android", onClick = { platform = "android" })
            Text("Android App", modifier = Modifier.padding(top = 12.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = priceStr, onValueChange = { priceStr = it }, label = { Text("Price") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = externalLink, onValueChange = { externalLink = it }, label = { Text("External URL Link") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                Text(if (imageUri.isBlank()) "Pick Image" else "Change Image")
            }
        }
        
        if (imageUri.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = imageUri,
                contentDescription = "Selected image",
                modifier = Modifier.size(100.dp),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val price = priceStr.toDoubleOrNull() ?: 0.0
                if (productId == null) {
                    productViewModel.addProduct(name, platform, description, price, externalLink, imageUri)
                } else {
                    editingProduct?.let {
                        productViewModel.updateProduct(
                            it.copy(
                                name = name,
                                platform = platform,
                                description = description,
                                price = price,
                                externalLink = externalLink,
                                imageUri = imageUri
                            )
                        )
                    }
                }
                onNavigateBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (productId == null) "Save Product" else "Update Product")
        }
    }
}
