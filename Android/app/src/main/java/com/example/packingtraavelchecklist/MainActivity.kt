package com.example.packingtraavelchecklist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.packingtraavelchecklist.ui.theme.PackingTraavelChecklistTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PackingTraavelChecklistTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // 改呼叫支援滑動與分頁的 MainPagerScreen
                    MainPagerScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}