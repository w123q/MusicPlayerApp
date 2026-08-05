package com.example.yourproject // 請保持你原本的 package 名稱

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.businesscard.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() { // 1. 確認繼承 AppCompatActivity，且開頭大括號在這裡

    // 2. lateinit 變數不可以給予 `= null` 的初始值
    private lateinit var binding: ActivityMainBinding

    // 3. onCreate 必須包含在 class 的大括號之內
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 4. 初始化 ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 其他元件初始化邏輯（如 findViewById 或 binding 操作）
    }
} // 5. class 的結尾大括號放在最下面