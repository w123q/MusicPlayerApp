package DadaModel
import androidx.compose.ui.graphics.Color

enum class CardCategory(
    val title: String,
    val defaultColor: Color,
    val sticker: String,
    val defaultMsg: String
) {
    BIRTHDAY("生日祝賀", Color(0xFFFFD1DC), "🎂", "生日快樂！願你平安喜樂"),
    CHRISTMAS("聖誕佳節", Color(0xFFC8E6C9), "🎄", "聖誕快樂！Merry Christmas"),
    LOVE("情人 / 週年", Color(0xFFFFCDD2), "💖", "情人節快樂！天天開心"),
    THANK_YOU("感謝卡片", Color(0xFFE1BEE7), "💌", "真心感謝你的幫助！")
}