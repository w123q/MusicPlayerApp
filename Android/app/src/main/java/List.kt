package com.example.packingtraavelchecklist

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

// 資料結構
data class ChecklistItemData(
    val id: Int,
    val title: String,
    val icon: ImageVector,
    var isChecked: Boolean = false
)

// 緊急電話資料結構
data class EmergencyContact(
    val title: String,
    val number: String,
    val description: String
)

@Composable
fun MainPagerScreen(modifier: Modifier = Modifier) {
    // 增加第 4 個頁籤：設定
    val tabs = listOf("旅遊清單", "生活清單", "緊急撥號", "設定")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    // 旅遊清單
    val travelItems = remember {
        mutableStateListOf(
            ChecklistItemData(1, "護照 / 身分證", Icons.Default.AccountBox),
            ChecklistItemData(2, "行李箱", Icons.Default.ShoppingBag),
            ChecklistItemData(3, "飯店確認單", Icons.Default.Home),
            ChecklistItemData(4, "女性生理用品（衛生棉/棉條）", Icons.Default.Info)
        )
    }

    // 生活清單
    val dailyItems = remember {
        mutableStateListOf(
            ChecklistItemData(101, "買牛奶", Icons.Default.ShoppingCart),
            ChecklistItemData(102, "繳電費", Icons.Default.Lightbulb),
            ChecklistItemData(103, "倒垃圾", Icons.Default.Home),
            ChecklistItemData(104, "補充生理用品（衛生棉/護墊）", Icons.Default.Info)
        )
    }

    // 緊急撥號列表
    val emergencyContacts = listOf(
        EmergencyContact("報案專線", "110", "警察局、犯罪報案與緊急救助"),
        EmergencyContact("消防 / 救護車", "119", "火災、救護車緊急送醫"),
        EmergencyContact("急難救助 / 查號", "112", "行動電話全球緊急救助號碼"),
        EmergencyContact("婦幼保護專線", "113", "家暴、性侵害及保護諮詢")
    )

    Column(modifier = modifier.fillMaxSize()) {
        // 頂部分頁籤（可滑動以適應4個頁籤）
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color(0xFF4FC3F7),
            contentColor = Color.Black
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold) }
                )
            }
        }

        // 左右滑動頁面（共四頁）
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> ChecklistPageLayout(
                    title = "旅  遊  清  單",
                    backgroundColor = Color(0xFF81D4FA), // 淡藍色
                    items = travelItems
                )
                1 -> ChecklistPageLayout(
                    title = "生  活  清  單",
                    backgroundColor = Color(0xFFAED581), // 淡綠色
                    items = dailyItems
                )
                2 -> EmergencyCallPage(
                    contacts = emergencyContacts
                )
                3 -> SettingsPage(
                    onClearCheckedItems = {
                        travelItems.removeAll { it.isChecked }
                        dailyItems.removeAll { it.isChecked }
                    },
                    onResetAll = {
                        travelItems.forEachIndexed { idx, item -> travelItems[idx] = item.copy(isChecked = false) }
                        dailyItems.forEachIndexed { idx, item -> dailyItems[idx] = item.copy(isChecked = false) }
                    }
                )
            }
        }
    }
}

// 通用清單頁面
@Composable
fun ChecklistPageLayout(
    title: String,
    backgroundColor: Color,
    items: MutableList<ChecklistItemData>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp)
    ) {
        Text(
            text = title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items, key = { it.id }) { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = item.isChecked,
                        onCheckedChange = { isChecked ->
                            val index = items.indexOf(item)
                            if (index != -1) {
                                items[index] = item.copy(isChecked = isChecked)
                            }
                        },
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = Color.Black,
                            checkedColor = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = Color.Black,
                        modifier = Modifier.size(36.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = item.title,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

// 緊急撥號頁面
@Composable
fun EmergencyCallPage(contacts: List<EmergencyContact>) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF8A80)) // 紅色警告背景
            .padding(24.dp)
    ) {
        Text(
            text = "緊  急  撥  號",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "點擊按鈕即可快速叫出電話撥號畫面",
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.9f),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(contacts) { contact ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${contact.title} (${contact.number})",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = contact.description,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }

                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:${contact.number}")
                            }
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "撥號",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("撥打", color = Color.White)
                    }
                }
            }
        }
    }
}

// 設定頁面
@Composable
fun SettingsPage(
    onClearCheckedItems: () -> Unit,
    onResetAll: () -> Unit
) {
    var isNotificationEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCFD8DC)) // 灰色主題背景
            .padding(24.dp)
    ) {
        Text(
            text = "設  定",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 通知開關
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Settings, contentDescription = null, tint = Color.Black)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("行程提醒通知", fontSize = 16.sp, color = Color.Black)
                        }
                        Switch(
                            checked = isNotificationEnabled,
                            onCheckedChange = { isNotificationEnabled = it }
                        )
                    }
                }
            }

            // 取消勾選所有項目
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.Black)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("取消勾選所有清單", fontSize = 16.sp, color = Color.Black)
                        }
                        Button(
                            onClick = onResetAll,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1))
                        ) {
                            Text("重置")
                        }
                    }
                }
            }

            // 刪除已勾選項目
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("清理已完成項目", fontSize = 16.sp, color = Color.Red)
                        }
                        Button(
                            onClick = onClearCheckedItems,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text("清除")
                        }
                    }
                }
            }

            // 版本資訊
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("關於 Packing & Travel Checklist", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("版本：1.0.0", fontSize = 14.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}