package com.example.rentalapkgamesyaban.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rentalapkgamesyaban.ui.viewmodel.RentalViewModel

@Composable
fun AdminDashboardScreen(
    rentalViewModel: RentalViewModel,
    onNavigateToProducts: () -> Unit,
    onNavigateToOrders: () -> Unit
) {
    val totalOrders by rentalViewModel.totalOrdersCount.collectAsState()
    val pendingRentals by rentalViewModel.pendingRentals.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Admin Dashboard", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Total Rentals (All Time)", style = MaterialTheme.typography.titleMedium)
                Text("$totalOrders", style = MaterialTheme.typography.displayMedium)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Pending Orders", style = MaterialTheme.typography.titleMedium)
                Text("${pendingRentals.size}", style = MaterialTheme.typography.displayMedium)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onNavigateToProducts, modifier = Modifier.fillMaxWidth()) {
            Text("Manage Products")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToOrders, modifier = Modifier.fillMaxWidth()) {
            Text("Confirm Orders")
        }
    }
}
