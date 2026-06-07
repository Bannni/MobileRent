package com.example.rentalapkgamesyaban

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.rentalapkgamesyaban.data.AppDatabase
import com.example.rentalapkgamesyaban.navigation.NavGraph
import com.example.rentalapkgamesyaban.repository.CartRepository
import com.example.rentalapkgamesyaban.repository.ProductRepository
import com.example.rentalapkgamesyaban.repository.RentalRepository
import com.example.rentalapkgamesyaban.repository.UserRepository
import com.example.rentalapkgamesyaban.ui.theme.RentSphereTheme
import com.example.rentalapkgamesyaban.ui.viewmodel.AuthViewModel
import com.example.rentalapkgamesyaban.ui.viewmodel.CartViewModel
import com.example.rentalapkgamesyaban.ui.viewmodel.ProductViewModel
import com.example.rentalapkgamesyaban.ui.viewmodel.RentalViewModel
import com.example.rentalapkgamesyaban.ui.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getInstance(this)
        val userRepository = UserRepository(database.userDao())
        val productRepository = ProductRepository(database.productDao())
        val cartRepository = CartRepository(database.cartDao())
        val rentalRepository = RentalRepository(database.rentalDao())

        val viewModelFactory = ViewModelFactory(
            userRepository, productRepository, cartRepository, rentalRepository
        )

        val authViewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]
        val productViewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]
        val cartViewModel = ViewModelProvider(this, viewModelFactory)[CartViewModel::class.java]
        val rentalViewModel = ViewModelProvider(this, viewModelFactory)[RentalViewModel::class.java]

        setContent {
            RentSphereTheme {
                NavGraph(
                    authViewModel = authViewModel,
                    productViewModel = productViewModel,
                    cartViewModel = cartViewModel,
                    rentalViewModel = rentalViewModel
                )
            }
        }
    }
}