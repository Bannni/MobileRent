package com.example.rentalapkgamesyaban.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomNavBar(
    role: String,
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        if (role == "admin") {
            NavigationBarItem(
                selected = currentRoute == "admin_dashboard",
                onClick = { onNavigate("admin_dashboard") },
                icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
                label = { Text("Dashboard") }
            )
            NavigationBarItem(
                selected = currentRoute == "manage_products",
                onClick = { onNavigate("manage_products") },
                icon = { Icon(Icons.Default.List, contentDescription = "Products") },
                label = { Text("Products") }
            )
            NavigationBarItem(
                selected = currentRoute == "order_confirmation",
                onClick = { onNavigate("order_confirmation") },
                icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Orders") },
                label = { Text("Orders") }
            )
        } else {
            NavigationBarItem(
                selected = currentRoute == "home",
                onClick = { onNavigate("home") },
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text("Home") }
            )
            NavigationBarItem(
                selected = currentRoute == "cart",
                onClick = { onNavigate("cart") },
                icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
                label = { Text("Cart") }
            )
            NavigationBarItem(
                selected = currentRoute == "library",
                onClick = { onNavigate("library") },
                icon = { Icon(Icons.Default.List, contentDescription = "Library") },
                label = { Text("Library") }
            )
        }
    }
}
