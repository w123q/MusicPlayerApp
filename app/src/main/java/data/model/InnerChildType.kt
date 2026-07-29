package data.model

class InnerChildType {
    package com.example.innerchild.data.model // ⚠️ 請確認 Package 名稱與你的專案一致

    import android.os.Parcelable
    import kotlinx.parcelize.Parcelize

    /**
     * 內在小孩類型資料模型
     */
    @Parcelize
    data class InnerChildType(
        val id: Int,
        val name: String,             // 中文類型名稱（如：討好型小孩）
        val title: String,            // 英文名稱（如：The Pleaser）
        val description: String,      // 特徵描述
        val coreWound: String,        // 核心傷口 / 渴望
        val dailyAffirmation: String, // 每日療癒肯定語
        val iconResId: Int            // 圖示資源 ID (如 R.drawable.ic_pleaser)
    ) : Parcelable
}