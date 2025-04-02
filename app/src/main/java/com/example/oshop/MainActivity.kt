package com.example.oshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.oshop.ui.theme.OshopTheme
import androidx.compose.foundation.layout.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OshopTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->  // Vietoje `it`, naudok aiškesnį pavadinimą
                    Box(modifier = Modifier.padding(paddingValues)) {
                        fallingCoins()
                    }
                }
            }
        }
    }
}


