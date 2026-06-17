package com.example.quizapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.adapter.ReviewAdapter
import com.example.quizapp.databinding.ActivityResultBinding
import com.example.quizapp.model.QuestionRepository

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score = intent.getIntExtra("SCORE", 0)
        val total = intent.getIntExtra("TOTAL", 0)
        val time = intent.getLongExtra("TIME", 0)
        val category = intent.getStringExtra("CATEGORY") ?: ""
        
        val percentage = if (total > 0) (score.toDouble() / total * 100).toInt() else 0

        binding.tvScore.text = "Điểm: $score/$total ($percentage%)"
        
        // Hiển thị thời gian hoàn thành
        val timeText = if (time < 60) "${time} giây" else "${time / 60} phút ${time % 60} giây"
        binding.tvResultTitle.text = "Hoàn thành trong: $timeText"

        saveHighScore(score)

        binding.btnRestart.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java).apply {
                putExtra("CATEGORY", category)
            }
            startActivity(intent)
            finish()
        }

        binding.btnHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        setupReviewRecyclerView(category)
    }

    private fun saveHighScore(score: Int) {
        val sharedPref = getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)
        val currentHigh = sharedPref.getInt("high_score", 0)
        if (score > currentHigh) {
            sharedPref.edit().putInt("high_score", score).apply()
        }
    }

    private fun setupReviewRecyclerView(category: String) {
        val questions = QuestionRepository.getQuestions(category)
        val adapter = ReviewAdapter(questions)
        binding.rvReview.layoutManager = LinearLayoutManager(this)
        binding.rvReview.adapter = adapter
    }
}
