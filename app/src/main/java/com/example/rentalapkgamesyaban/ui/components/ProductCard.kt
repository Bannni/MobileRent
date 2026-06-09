package com.example.rentalapkgamesyaban.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.rentalapkgamesyaban.data.entity.ProductEntity
import com.example.rentalapkgamesyaban.ui.theme.EmeraldGreen
import com.example.rentalapkgamesyaban.ui.theme.SoftBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(
    product: ProductEntity,
    onClick: () -> Unit,
    onAddToCart: (() -> Unit)? = null,
    onEdit: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (product.imageUri.isNotBlank()) {
                coil.compose.AsyncImage(
                    model = product.imageUri,
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxWidth().height(150.dp).padding(bottom = 8.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Badge(containerColor = if (product.platform == "game") EmeraldGreen else SoftBlue) {
                    Text(product.platform.uppercase(), modifier = Modifier.padding(4.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Rp ${product.price}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = SoftBlue
                )
                Row {
                    onAddToCart?.let {
                        Button(
                            onClick = it,
                            colors = ButtonDefaults.buttonColors(containerColor = SoftBlue)
                        ) {
                            Text("Add to Cart")
                        }
                    }
                    onEdit?.let {
                        TextButton(onClick = it) {
                            Text("Edit")
                        }
                    }
                    onDelete?.let {
                        TextButton(onClick = it, colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}
