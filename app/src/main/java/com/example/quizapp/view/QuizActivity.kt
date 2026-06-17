package com.example.quizapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityQuizBinding
import com.example.quizapp.viewmodel.QuizViewModel

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    private val viewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category = intent.getStringExtra("CATEGORY") ?: "Kotlin Basics"
        viewModel.initQuiz(category)

        observeViewModel()

        binding.btnSubmit.setOnClickListener {
            val selectedId = binding.rgOptions.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(this, "Vui lòng chọn một đáp án!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val radioButton = findViewById<RadioButton>(selectedId)
            val index = binding.rgOptions.indexOfChild(radioButton)
            
            val isCorrect = viewModel.checkAnswer(index)
            val questions = viewModel.questions.value
            val currentQ = questions?.get(viewModel.currentQuestionIndex.value ?: 0)
            
            val message = if (isCorrect) "Đúng rồi! " else "Sai rồi! Đáp án đúng là: ${currentQ?.options?.get(currentQ.correctAnswerIndex)}"
            Toast.makeText(this, message + "\n" + (currentQ?.explanation ?: ""), Toast.LENGTH_LONG).show()
            
            viewModel.nextQuestion()
            binding.rgOptions.clearCheck()
        }
    }

    private fun observeViewModel() {
        viewModel.questions.observe(this) { questions ->
            binding.progressBar.max = questions.size
            updateQuestion(viewModel.currentQuestionIndex.value ?: 0)
        }

        viewModel.currentQuestionIndex.observe(this) { index ->
            updateQuestion(index)
        }

        viewModel.timeLeft.observe(this) { seconds ->
            binding.tvTimer.text = "${seconds}s"
        }

        viewModel.isQuizFinished.observe(this) { finished ->
            if (finished) {
                val intent = Intent(this, ResultActivity::class.java).apply {
                    putExtra("SCORE", viewModel.score.value ?: 0)
                    putExtra("TOTAL", viewModel.questions.value?.size ?: 0)
                    putExtra("TIME", viewModel.totalTimeSeconds)
                    putExtra("CATEGORY", intent.getStringExtra("CATEGORY"))
                }
                startActivity(intent)
                finish()
            }
        }
    }

    private fun updateQuestion(index: Int) {
        val questions = viewModel.questions.value ?: return
        if (index >= questions.size) return

        val question = questions[index]
        binding.tvProgress.text = "Câu ${index + 1}/${questions.size}"
        binding.progressBar.progress = index + 1
        binding.tvQuestion.text = question.text
        
        binding.rbOption1.text = question.options[0]
        binding.rbOption2.text = question.options[1]
        binding.rbOption3.text = question.options[2]
        binding.rbOption4.text = question.options[3]
    }
}
