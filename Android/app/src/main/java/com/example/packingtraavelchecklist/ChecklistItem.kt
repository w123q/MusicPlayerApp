package com.example.packingtraavelchecklist

data class ChecklistItem(
    val id: Int,
    val title: String,
    val category: String, // "travel" 代表旅遊類，"daily" 代表生活類
    val isChecked: Boolean = false
)