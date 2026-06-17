package com.example.quizapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.databinding.ItemQuestionReviewBinding
import com.example.quizapp.model.Question

class ReviewAdapter(private val questions: List<Question>) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemQuestionReviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemQuestionReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position]
        holder.binding.tvReviewQuestion.text = "${position + 1}. ${question.text}"
        holder.binding.tvReviewCorrectAnswer.text = "Đáp án đúng: ${question.options[question.correctAnswerIndex]}"
        holder.binding.tvReviewExplanation.text = "Giải thích: ${question.explanation}"
    }

    override fun getItemCount() = questions.size
}
