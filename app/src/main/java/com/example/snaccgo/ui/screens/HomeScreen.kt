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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.snaccgo.R
import com.example.snaccgo.model.category.CategoryModel
import com.example.snaccgo.repository.MainRepository
import com.example.snaccgo.ui.composables.SnaccGoBottomNavigation
import com.example.snaccgo.ui.theme.SnaccGoTheme
import com.example.snaccgo.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel
) {
    val categories by viewModel.snaccCategoryList
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            ProfileHeader()
            Search()
            Banner()
            CategorySection(
                categories = categories, 
                isLoading = isLoading,
                error = error,
                onRetry = { viewModel.refreshCategories() }
            )

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
fun CategorySection(
    categories: List<CategoryModel>,
    isLoading: Boolean,
    error: String?,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Categories",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Error: $error",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Button(onClick = onRetry) {
                        Text("Retry")
                    }
                }
            }
        } else if (categories.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No categories found", textAlign = TextAlign.Center)
            }
        } else {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(categories) { category ->
                    CategoryItem(category = category)
                }
            }
        }
    }
}

@Composable
fun CategoryItem(category: CategoryModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentSize()
            .padding(vertical = 4.dp)
    ) {
        Card(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(6.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = category.picUrl,
                    contentDescription = category.title,
                    modifier = Modifier
                        .size(45.dp)
                        .padding(6.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
        Text(
            text = category.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Composable
fun Banner() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.banner),
            contentDescription = "Banner Image",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentScale = ContentScale.Crop
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
            .padding(horizontal = 8.dp, vertical = 4.dp),
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
                        fontSize = 14.sp,
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
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side - Text column
        Column(
            modifier = Modifier.padding(start = 4.dp)
        ) {
            Text(
                text = "Hi Ujjwal",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Order & Eat",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        // Right side - Profile image
        Image(
            painter = painterResource(id = R.drawable.human), 
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(50.dp)
                .padding(2.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    SnaccGoTheme {
        HomeScreen(viewModel = MainViewModel(MainRepository()))
    }
}