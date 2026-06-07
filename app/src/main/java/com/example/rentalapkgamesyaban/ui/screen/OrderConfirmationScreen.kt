package com.example.rentalapkgamesyaban.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rentalapkgamesyaban.ui.theme.EmeraldGreen
import com.example.rentalapkgamesyaban.ui.viewmodel.RentalViewModel

@Composable
fun OrderConfirmationScreen(
    rentalViewModel: RentalViewModel
) {
    val pendingRentals by rentalViewModel.pendingRentals.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Pending Orders", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (pendingRentals.isEmpty()) {
            Text("No pending orders.")
        } else {
            LazyColumn {
                items(pendingRentals) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Product: ${item.product.name}", style = MaterialTheme.typography.titleMedium)
                            Text("User ID: ${item.rental.userId}")
                            Text("Notes: ${item.rental.notes}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { rentalViewModel.approveRental(item.rental.id) },
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Setujui (Approve)")
                            }
                        }
                    }
                }
            }
        }
    }
}
