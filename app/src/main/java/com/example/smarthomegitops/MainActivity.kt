package com.example.smarthomegitops

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthomegitops.ui.UiState
import com.example.smarthomegitops.ui.MainViewModel



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                SmartHomeMainScreen()
            }
        }
    }
}
@Composable
fun SmartHomeMainScreen(viewModel: MainViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    val backgrundColor = when (uiState) {
        is UiState.Normal -> Color(0xFF2E7D32)
        is UiState.SecurityAlert -> Color(0xFFC62828)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgrundColor)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    )   {
        when (val state = uiState) {
            is UiState.Normal -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "STATUS OF THE SYSTEM: NORMAL",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Monitoring GitHub repository for anomalies... ",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                    )
                }
            }
            is UiState.SecurityAlert -> {
                Column( horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "CONFIDENCE SCORE: ${state.confidenceScore}% ",
                        color = Color.Yellow,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "AI THREAT COMMENT: ",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "\"${state.rawText}\"",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}