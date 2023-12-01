package com.example.tokoku

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomBarScreen(
    val route:String,
    val title: String,
    val icon: ImageVector
) {
    object Form : BottomBarScreen(
        route = "form",
        title = "Form",
        icon = Icons.Rounded.Add
    )

    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Rounded.Home
    )

    object CastReceipt : BottomBarScreen(
        route = "cashReceipt",
        title ="Kasbon",
        icon = Icons.Rounded.ShoppingCart
    )
}