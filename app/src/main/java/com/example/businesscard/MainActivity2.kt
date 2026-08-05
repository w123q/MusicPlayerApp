package com.example.yourproject // 請保持你原本的 package 名稱

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.businesscard.databinding.ActivityMainBinding
import android.util.Log
import com.example.businesscard.R

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
        fun enter(view: View) {
        val str=binding.name.id.toString().toString()
            val tag = "MainActivity2"
            Log.d(tag, getString(R.string.str_Text))
        }
    }
} // 5. class 的結尾大括號放在最下面