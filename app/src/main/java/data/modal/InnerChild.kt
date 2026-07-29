package data.modal

class InnerChild {
    enum class EnneagramType(
        val typeNumber: Int,
        val title: String,
        val nickname: String
    ) {
        TYPE_1(1, "完美主義者", "追求卓越的孩子"),
        TYPE_2(2, "給予者", "渴望被愛的孩子"),
        TYPE_3(3, "成就者", "期待被看見的孩子"),
        TYPE_4(4, "浪漫者", "獨一無二的孩子"),
        TYPE_5(5, "觀察者", "尋求安全感的孩子"),
        TYPE_6(6, "忠誠者", "尋求依賴的孩子"),
        TYPE_7(7, "熱情者", "害怕被限制的孩子"),
        TYPE_8(8, "挑戰者", "保護自己的孩子"),
        TYPE_9(9, "和平者", "害怕衝突的孩子");

        companion object {
            fun fromNumber(number: Int): EnneagramType {
                return entries.find { it.typeNumber == number } ?: TYPE_9
            }
        }
    }
}