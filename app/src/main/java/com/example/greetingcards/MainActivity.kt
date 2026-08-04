package com.example.greetingcardapp // 👈 請改成你自己的 package 名稱

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                CardAppScreen(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

@Composable
fun CardAppScreen(modifier: Modifier = Modifier) {
    // 1. 宣告 State (狀態) - 當這些數值改變，UI 就會自動重繪
    var name by remember { mutableStateOf("小明") }
    var selectedColor by remember { mutableStateOf(Color(0xFFFFD1DC)) } // 預設柔和粉紅
    var showSticker by remember { mutableStateOf(true) } // 是否顯示驚喜貼紙

    // 顏色選單
    val colorOptions = listOf(
        Color(0xFFFFD1DC), // 粉紅
        Color(0xFFE2F0CB), // 淺綠
        Color(0xFFC7CEEA), // 淺藍
        Color(0xFFFFDAC1)  // 蜜桃橘
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "🎉 我的節日賀卡產生器",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 2. 賀卡預覽卡片
        Card(
            modifier = Modifier
                .size(width = 280.dp, height = 360.dp),
            colors = CardDefaults.cardColors(containerColor = selectedColor),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                // 卡片中央文字
                Text(
                    text = "祝 $name\n節日快樂！✨",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp,
                    color = Color(0xFF333333)
                )

                // 驚喜動畫貼紙（Level 2 的 AnimatedVisibility）
                androidx.compose.animation.AnimatedVisibility(
                    visible = showSticker,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut(),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(
                        text = "🎁",
                        fontSize = 36.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 3. 輸入名字欄位
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("輸入收件人姓名") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.85f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 4. 卡片顏色選擇器
        Text("選擇卡片顏色：", fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            colorOptions.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = if (selectedColor == color) 3.dp else 0.dp,
                            color = if (selectedColor == color) Color.DarkGray else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable { selectedColor = color }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 5. 切換貼紙開關
        Button(onClick = { showSticker = !showSticker }) {
            Text(if (showSticker) "隱藏右上角禮物" else "加上右上角禮物")
        }
    }
}