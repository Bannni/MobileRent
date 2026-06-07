package com.example.rentalapkgamesyaban.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.rentalapkgamesyaban.data.entity.UserEntity
import com.example.rentalapkgamesyaban.ui.viewmodel.CartViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    currentUser: UserEntity,
    cartViewModel: CartViewModel,
    snackbarHostState: SnackbarHostState
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    var notes by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(currentUser.id) {
        cartViewModel.loadCart(currentUser.id)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Your Cart", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (cartItems.isEmpty()) {
            Text("Your cart is empty.", modifier = Modifier.fillMaxWidth())
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.product.name, fontWeight = FontWeight.Bold)
                                Text("Rp ${item.product.price}", color = MaterialTheme.colorScheme.primary)
                            }
                            TextButton(onClick = { cartViewModel.removeFromCart(item.cart) }) {
                                Text("Remove", color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }

            val totalPrice = cartItems.sumOf { it.product.price }
            
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Rental Notes / Requirements") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total: Rp $totalPrice", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Button(onClick = {
                    cartViewModel.checkout(currentUser.id, notes)
                    notes = ""
                    scope.launch {
                        snackbarHostState.showSnackbar("Checkout successful! Waiting for admin approval.")
                    }
                }) {
                    Text("Checkout")
                }
            }
        }
    }
}
