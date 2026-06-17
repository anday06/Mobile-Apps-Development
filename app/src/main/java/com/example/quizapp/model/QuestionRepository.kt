package com.example.quizapp.model

object QuestionRepository {
    fun getCategories() = listOf("Kotlin Basics", "Android UI", "Ngôn ngữ khác")

    fun getQuestions(category: String): List<Question> {
        val allQuestions = listOf(
            // Kotlin Basics
            Question(1, "Từ khóa nào được dùng để khai báo hằng số trong Kotlin?", listOf("var", "val", "const val", "let"), 2, "const val được dùng cho hằng số compile-time.", "Kotlin Basics"),
            Question(2, "Kotlin được phát triển bởi công ty nào?", listOf("Google", "Oracle", "JetBrains", "Facebook"), 2, "JetBrains là công ty tạo ra Kotlin.", "Kotlin Basics"),
            Question(3, "Đâu là cách khai báo một biến có thể null trong Kotlin?", listOf("String", "String?", "Null<String>", "Optional<String>"), 1, "Sử dụng dấu ? để cho phép giá trị null.", "Kotlin Basics"),
            Question(4, "Hàm nào được dùng để in ra màn hình và xuống dòng?", listOf("print()", "println()", "write()", "log()"), 1, "println() in nội dung và thêm ký tự xuống dòng.", "Kotlin Basics"),
            Question(5, "Lớp nào là lớp cha của tất cả các lớp trong Kotlin?", listOf("Object", "Any", "Base", "Root"), 1, "Any là lớp gốc của mọi lớp trong Kotlin.", "Kotlin Basics"),
            
            // Android UI
            Question(6, "Layout nào giúp tối ưu hóa phân cấp view phẳng?", listOf("LinearLayout", "RelativeLayout", "ConstraintLayout", "FrameLayout"), 2, "ConstraintLayout giúp giảm lồng ghép view.", "Android UI"),
            Question(7, "Thành phần nào dùng để hiển thị danh sách lớn hiệu quả?", listOf("ScrollView", "ListView", "RecyclerView", "ViewPager"), 2, "RecyclerView tái sử dụng view để tiết kiệm bộ nhớ.", "Android UI"),
            Question(8, "Đơn vị đo kích thước chữ khuyến khích trong Android là gì?", listOf("dp", "px", "sp", "pt"), 2, "sp (scale-independent pixels) giúp chữ thay đổi theo cài đặt hệ thống.", "Android UI"),
            Question(9, "Thuộc tính nào dùng để căn giữa view trong Parent?", listOf("layout_center", "gravity", "layout_gravity", "layout_constraintCenter"), 3, "Trong ConstraintLayout dùng các ràng buộc Center.", "Android UI"),
            Question(10, "Thư viện nào là bộ công cụ UI hiện đại của Android?", listOf("XML", "Jetpack Compose", "Swing", "JavaFX"), 1, "Jetpack Compose là toolkit khai báo UI hiện đại của Google.", "Android UI")
        )
        
        return allQuestions.filter { it.category == category }.shuffled().take(5) // Lấy 5 câu mỗi chủ đề để test nhanh
    }
}
