package com.example.rentalapkgamesyaban.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    
    // Buyer
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Library : Screen("library")
    
    // Admin
    object AdminDashboard : Screen("admin_dashboard")
    object ManageProducts : Screen("manage_products")
    object AddEditProduct : Screen("add_edit_product")
    object OrderConfirmation : Screen("order_confirmation")
}
