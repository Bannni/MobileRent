package com.example.rentalapkgamesyaban.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rentalapkgamesyaban.data.entity.UserEntity
import com.example.rentalapkgamesyaban.ui.components.ProductCard
import com.example.rentalapkgamesyaban.ui.viewmodel.CartViewModel
import com.example.rentalapkgamesyaban.ui.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    currentUser: UserEntity,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    snackbarHostState: SnackbarHostState
) {
    val products by productViewModel.products.collectAsState()
    val searchQuery by productViewModel.searchQuery.collectAsState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { productViewModel.setSearchQuery(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search Games & Apps...") },
            singleLine = true
        )

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    onClick = { /* Could navigate to details */ },
                    onAddToCart = {
                        cartViewModel.addToCart(currentUser.id, product.id)
                        scope.launch {
                            snackbarHostState.showSnackbar("${product.name} added to cart")
                        }
                    }
                )
            }
        }
    }
}
