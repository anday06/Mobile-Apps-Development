package com.example.quizapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.model.QuestionRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val topics = QuestionRepository.getCategories()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, topics)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTopics.adapter = adapter

        binding.btnStart.setOnClickListener {
            val selectedTopic = binding.spinnerTopics.selectedItem.toString()
            val intent = Intent(this, QuizActivity::class.java).apply {
                putExtra("CATEGORY", selectedTopic)
            }
            startActivity(intent)
        }

        binding.btnHighScore.setOnClickListener {
            val sharedPref = getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)
            val highScore = sharedPref.getInt("high_score", 0)
            Toast.makeText(this, "Điểm cao nhất: $highScore", Toast.LENGTH_LONG).show()
        }
    }
}
