package com.example.newsapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.core.presentation.theme.SpendingTrackerAppTheme
import com.example.newsapp.core.presentation.util.Background
import com.example.newsapp.core.presentation.util.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpendingTrackerAppTheme {
                    Navigation(modifier = Modifier.fillMaxSize())
            }
        }
    }

    @Composable
    fun Navigation(modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        Background()
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = Screen.SPENDING_OVERVIEW.name
        ) {
            composable(route = Screen.SPENDING_OVERVIEW.name) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Spending Overview")
                }
            }
            composable(route = Screen.SPENDING_DETAILS.name) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Spending Detail")
                }
            }
            composable(route = Screen.SPENDING_OVERVIEW.name) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                }
            }
        }

    }
}
