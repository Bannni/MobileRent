package com.example.rentalapkgamesyaban.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.rentalapkgamesyaban.data.entity.UserEntity
import com.example.rentalapkgamesyaban.ui.theme.EmeraldGreen
import com.example.rentalapkgamesyaban.ui.viewmodel.RentalViewModel

@Composable
fun LibraryScreen(
    currentUser: UserEntity,
    rentalViewModel: RentalViewModel
) {
    val myRentals by rentalViewModel.myRentals.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(currentUser.id) {
        rentalViewModel.loadMyRentals(currentUser.id)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("My Library", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (myRentals.isEmpty()) {
            Text("You haven't rented anything yet.")
        } else {
            LazyColumn {
                items(myRentals) { rentalItem ->
                    val statusColor = when (rentalItem.rental.status) {
                        "active" -> EmeraldGreen
                        "pending" -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(rentalItem.product.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Badge(containerColor = statusColor) {
                                    Text(rentalItem.rental.status.uppercase(), modifier = Modifier.padding(4.dp))
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            if (rentalItem.rental.status == "active") {
                                Button(
                                    onClick = {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(rentalItem.product.externalLink))
                                        context.startActivity(intent)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Buka Link")
                                }
                            } else if (rentalItem.rental.status == "expired") {
                                Text("Rental has expired. Please rent again.", color = MaterialTheme.colorScheme.error)
                            } else {
                                Text("Waiting for admin approval...")
                            }
                        }
                    }
                }
            }
        }
    }
}
