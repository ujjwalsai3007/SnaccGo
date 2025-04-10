package com.example.snaccgo.ui.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Info

@Composable
fun SnaccGoBottomNavigation(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Profile", "Cart", "Support", "Settings")
    val routes = listOf("home", "profile", "cart", "support", "settings")
    
    // Update selected item based on current route
    val currentIndex = routes.indexOf(currentRoute).takeIf { it >= 0 } ?: 0
    selectedItem = currentIndex
    
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { 
                    when (index) {
                        0 -> Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(24.dp)
                        )
                        1 -> Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.size(24.dp)
                        )
                        2 -> Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Cart",
                            modifier = Modifier.size(24.dp)
                        )
                        3 -> Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Support",
                            modifier = Modifier.size(24.dp)
                        )
                        4 -> Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                label = { Text(text = item) },
                selected = selectedItem == index,
                onClick = { 
                    selectedItem = index
                    onNavigate(routes[index])
                }
            )
        }
    }
}

