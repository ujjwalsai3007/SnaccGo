package com.example.snaccgo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snaccgo.R
import com.example.snaccgo.ui.composables.SnaccGoBottomNavigation
import com.example.snaccgo.ui.theme.SnaccGoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var currentRoute by remember { mutableStateOf("home") }
    
    Scaffold(
        bottomBar = {
            SnaccGoBottomNavigation(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    currentRoute = route
                }
            )
        }
    ) { paddingValues ->
        // This is where your screen content goes
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            // Profile image at the top
            ProfileHeader()
            
            // Main content
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                when (currentRoute) {
                    "home" -> Text("Home Screen")
                    "profile" -> Text("Profile Screen")
                    "cart" -> Text("Cart Screen")
                    "support" -> Text("Support Screen")
                    "settings" -> Text("Settings Screen")
                }
            }
        }
    }
}

@Composable
fun ProfileHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side - Text column
        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = "Hi Ujjwal",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Order and Eat",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        // Right side - Profile image
        Image(
            painter = painterResource(id = R.drawable.human), 
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(70.dp)
                .padding(4.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    SnaccGoTheme {
        HomeScreen()
    }
} 