package com.example.rentalapkgamesyaban.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rentalapkgamesyaban.ui.viewmodel.ProductViewModel

@Composable
fun AddEditProductScreen(
    productViewModel: ProductViewModel,
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var platform by remember { mutableStateOf("game") }
    var description by remember { mutableStateOf("") }
    var priceStr by remember { mutableStateOf("") }
    var externalLink by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Add Product", style = MaterialTheme.typography.headlineMedium)
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

        Button(
            onClick = {
                val price = priceStr.toDoubleOrNull() ?: 0.0
                productViewModel.addProduct(name, platform, description, price, externalLink, "")
                onNavigateBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Product")
        }
    }
}
