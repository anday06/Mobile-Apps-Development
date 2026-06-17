package com.example.quizapp.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.model.Question
import com.example.quizapp.model.QuestionRepository

class QuizViewModel : ViewModel() {
    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> = _questions

    private val _currentQuestionIndex = MutableLiveData(0)
    val currentQuestionIndex: LiveData<Int> = _currentQuestionIndex

    private val _score = MutableLiveData(0)
    val score: LiveData<Int> = _score

    private val _timeLeft = MutableLiveData(30)
    val timeLeft: LiveData<Int> = _timeLeft

    private val _isQuizFinished = MutableLiveData(false)
    val isQuizFinished: LiveData<Boolean> = _isQuizFinished

    private var timer: CountDownTimer? = null
    private var startTimeMillis: Long = 0
    var totalTimeSeconds: Long = 0
        private set

    fun initQuiz(category: String) {
        _questions.value = QuestionRepository.getQuestions(category)
        startTimeMillis = System.currentTimeMillis()
        startTimer()
    }

    fun startTimer() {
        timer?.cancel()
        _timeLeft.value = 30
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timeLeft.value = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                nextQuestion()
            }
        }.start()
    }

    fun checkAnswer(selectedIndex: Int): Boolean {
        val currentQ = _questions.value?.get(_currentQuestionIndex.value ?: 0)
        val isCorrect = currentQ?.correctAnswerIndex == selectedIndex
        if (isCorrect) {
            _score.value = (_score.value ?: 0) + 1
        }
        return isCorrect
    }

    fun nextQuestion() {
        val currentIndex = _currentQuestionIndex.value ?: 0
        if (currentIndex < (_questions.value?.size ?: 0) - 1) {
            _currentQuestionIndex.value = currentIndex + 1
            startTimer()
        } else {
            finishQuiz()
        }
    }

    private fun finishQuiz() {
        totalTimeSeconds = (System.currentTimeMillis() - startTimeMillis) / 1000
        _isQuizFinished.value = true
        timer?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}
