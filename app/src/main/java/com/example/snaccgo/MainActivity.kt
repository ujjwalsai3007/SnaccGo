package com.example.snaccgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.snaccgo.repository.MainRepository
import com.example.snaccgo.ui.screens.HomeScreen
import com.example.snaccgo.ui.theme.SnaccGoTheme
import com.example.snaccgo.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Ensure proper edge-to-edge display
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        val mainRepository = MainRepository()
        val mainViewModel = MainViewModel(mainRepository)
        
        setContent {
            SnaccGoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(viewModel = mainViewModel)
                }
            }
        }
    }
}