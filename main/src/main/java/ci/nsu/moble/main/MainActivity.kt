package ci.nsu.moble.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ci.nsu.moble.main.ui.theme.PracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeTheme {
                ColorApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorApp() {
    val colorMap = mapOf(
        "red" to Color.Red,
        "orange" to Color(0xFFFFA500),
        "yellow" to Color.Yellow,
        "green" to Color.Green,
        "blue" to Color.Blue,
        "indigo" to Color(0xFF4B0082),
        "violet" to Color(0xFF800080)
    )

    var inputText by remember { mutableStateOf("") }
    var buttonColor by remember { mutableStateOf(Color.Black) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Введите цвет") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                val key = inputText.trim().lowercase()
                if (colorMap.containsKey(key)) {
                    buttonColor = colorMap[key]!!
                } else {
                    Log.d("ColorApp", "Цвет \"$inputText\" не найден")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(
                text = "Применить цвет",
                fontSize = 16.sp,
                color = if (buttonColor == Color(0xFFFFFF00)) Color.Black else Color.White
            )
        }

        Column(modifier = Modifier.padding(top = 24.dp)) {
            colorMap.forEach { (name, color) ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(bottom = 8.dp)
                        .background(color, RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = name.replaceFirstChar { it.uppercase() },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (name == "yellow") Color.Black else Color.White
                    )
                }
            }
        }
    }
}