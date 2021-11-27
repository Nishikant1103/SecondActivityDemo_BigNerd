package com.example.secondactivitydemo

import androidx.lifecycle.ViewModel

class QuizViewModel: ViewModel() {
    var mCurrentIndex = 0
    var isCheating = false
    var isAnswerTrue = false
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    val currentQuestionAnswer: Boolean
        get() = questionBank[mCurrentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[mCurrentIndex].textResId
    fun moveToNext() {
        mCurrentIndex = (mCurrentIndex + 1) % questionBank.size
    }
    fun moveToPrev() {
        mCurrentIndex = (mCurrentIndex - 1) % questionBank.size
    }

}