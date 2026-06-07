package com.example.rentalapkgamesyaban.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rentalapkgamesyaban.ui.components.BottomNavBar
import com.example.rentalapkgamesyaban.ui.screen.*
import com.example.rentalapkgamesyaban.ui.viewmodel.AuthViewModel
import com.example.rentalapkgamesyaban.ui.viewmodel.CartViewModel
import com.example.rentalapkgamesyaban.ui.viewmodel.ProductViewModel
import com.example.rentalapkgamesyaban.ui.viewmodel.RentalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    authViewModel: AuthViewModel,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    rentalViewModel: RentalViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val currentUser by authViewModel.currentUser.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route, Screen.Cart.route, Screen.Library.route,
        Screen.AdminDashboard.route, Screen.ManageProducts.route, Screen.OrderConfirmation.route
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (showBottomBar && currentUser != null) {
                BottomNavBar(
                    role = currentUser!!.role,
                    currentRoute = currentRoute ?: "",
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    authViewModel = authViewModel,
                    onLoginSuccess = { role ->
                        val dest = if (role == "admin") Screen.AdminDashboard.route else Screen.Home.route
                        navController.navigate(dest) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = { navController.navigate(Screen.Register.route) }
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen(
                    authViewModel = authViewModel,
                    onRegisterSuccess = { navController.navigateUp() },
                    onNavigateToLogin = { navController.navigateUp() }
                )
            }
            
            // Buyer Routes
            composable(Screen.Home.route) {
                currentUser?.let { user ->
                    HomeScreen(user, productViewModel, cartViewModel, snackbarHostState)
                }
            }
            composable(Screen.Cart.route) {
                currentUser?.let { user ->
                    CartScreen(user, cartViewModel, snackbarHostState)
                }
            }
            composable(Screen.Library.route) {
                currentUser?.let { user ->
                    LibraryScreen(user, rentalViewModel)
                }
            }

            // Admin Routes
            composable(Screen.AdminDashboard.route) {
                AdminDashboardScreen(
                    rentalViewModel = rentalViewModel,
                    onNavigateToProducts = { navController.navigate(Screen.ManageProducts.route) },
                    onNavigateToOrders = { navController.navigate(Screen.OrderConfirmation.route) }
                )
            }
            composable(Screen.ManageProducts.route) {
                ManageProductsScreen(
                    productViewModel = productViewModel,
                    onNavigateToAddProduct = { navController.navigate(Screen.AddEditProduct.route) }
                )
            }
            composable(Screen.AddEditProduct.route) {
                AddEditProductScreen(
                    productViewModel = productViewModel,
                    onNavigateBack = { navController.navigateUp() }
                )
            }
            composable(Screen.OrderConfirmation.route) {
                OrderConfirmationScreen(rentalViewModel = rentalViewModel)
            }
        }
    }
}
