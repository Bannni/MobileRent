package com.example.rentalapkgamesyaban.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rentalapkgamesyaban.ui.components.ProductCard
import com.example.rentalapkgamesyaban.ui.viewmodel.ProductViewModel

@Composable
fun ManageProductsScreen(
    productViewModel: ProductViewModel,
    onNavigateToAddProduct: (Int?) -> Unit
) {
    val products by productViewModel.products.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToAddProduct(null) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Product")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("Manage Products", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(products) { product ->
                    ProductCard(
                        product = product,
                        onClick = { /* Nothing for now */ },
                        onEdit = { onNavigateToAddProduct(product.id) },
                        onDelete = { productViewModel.deleteProduct(product) }
                    )
                }
            }
        }
    }
}
