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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
            Search()
            Banner()
            ;skwfhgnkjewnfkgvbdfwjkbnvkdbnfbvknwd;kbvnew
            ewrgndw;lfngv;ldwnfbvlkndwfbv
            kjewfbdvlkjdbglkvbdwflv
            ;bkvndfw;kjnblkwdfgbv
            wbnfewd;kjbvn
            
            // Main content
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                when (currentRoute) {
                    "home" -> Text("")
                    "profile" -> Text("")
                    "cart" -> Text("")
                    "support" -> Text("")
                    "settings" -> Text("Settings")
                }
            }
        }
    }
}

@Composable
fun Banner() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 19.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.banner),
            contentDescription = "Banner Image",
            modifier = Modifier
                .fillMaxWidth()        // Fill the parent width
                .wrapContentHeight(),  // Let height adjust based on aspect ratio
            contentScale = ContentScale.Fit  // Show the entire image with no cropping
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search() {
    var text by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = "Find Your Food",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                },
                leadingIcon = {
                    Image(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        modifier = Modifier.size(24.dp),
                        contentScale = ContentScale.Fit
                    )
                },
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.purple_500),
                    unfocusedBorderColor = Color.LightGray,
                    cursorColor = colorResource(id = R.color.purple_500)
                ),
                singleLine = true
            )
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
                text = "Order & Eat",
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