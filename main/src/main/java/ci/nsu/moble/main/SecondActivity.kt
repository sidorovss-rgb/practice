package ci.nsu.moble.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ci.nsu.moble.main.ui.theme.PracticeTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

// TODO: crate sealed class with 3 routes
sealed class BottomScreen(
    val route: String,
    val label: String
) {
    object Home : BottomScreen("home", "Home")
    object ScreenOne : BottomScreen("screen_one", "Screen One")
    object ScreenTwo : BottomScreen("screen_two", "Screen Two")
}

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeTheme {
                SecondActivityScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondActivityScreen() {
    // todo: create nav controller
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    var receivedText by remember { mutableStateOf("") }

    if (context is Activity) {
        receivedText = context.intent.getStringExtra("text_data") ?: "No text received"
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = { Text(receivedText) }, navigationIcon = {
                IconButton(onClick = {
                    // TODO: create intent and start MainActivity
                    val intent = Intent(context, MainActivity::class.java)
                    (context as Activity).startActivity(intent)
                    (context as Activity).finish()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Blue, titleContentColor = Color.White
            )
        )
    }, bottomBar = {
        NavigationBar {
            NavigationBarItem(
                icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = currentRoute == BottomScreen.Home.route,

                onClick = {
                    // TODO: navigate to home screen by navController
                    navController.navigate(BottomScreen.Home.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            NavigationBarItem(
                icon = { Icon(imageVector = Icons.Filled.List, contentDescription = "Screen One") },
                label = { Text("Screen One") },
                selected = currentRoute == BottomScreen.ScreenOne.route,

                onClick = {
                    // TODO: navigate to screen one
                    navController.navigate(BottomScreen.ScreenOne.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            NavigationBarItem(
                icon = { Icon(imageVector = Icons.Filled.Settings, contentDescription = "Screen Two") },
                label = { Text("Screen Two") },
                selected = currentRoute == BottomScreen.ScreenTwo.route,
                onClick = {
                    // TODO: navigate to screen two
                    navController.navigate(BottomScreen.ScreenTwo.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }) { innerPadding ->
        // TODO: create a nav graph with 3 screens
        NavHost(
            navController = navController,
            startDestination = BottomScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomScreen.Home.route) { HomeScreen() }
            composable(BottomScreen.ScreenOne.route) { ScreenOne() }
            composable(BottomScreen.ScreenTwo.route) { ScreenTwo() }
        }
    }
}

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Home Screen")
    }
}

@Composable
fun ScreenOne() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Screen One")
    }
}

@Composable
fun ScreenTwo() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Screen Two")
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PracticeTheme {
        SecondActivityScreen()
    }
}