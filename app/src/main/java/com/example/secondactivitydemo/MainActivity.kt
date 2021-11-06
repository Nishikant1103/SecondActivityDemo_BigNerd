package com.example.secondactivitydemo

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView
    private var mQuestionArrayList = ArrayList<Question>()
    private var mCurrentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        prevButton = findViewById(R.id.prev_button)

        mQuestionArrayList.add(Question(R.string.question_australia, true))
        mQuestionArrayList.add(Question(R.string.question_oceans, true))
        mQuestionArrayList.add(Question(R.string.question_mideast, false))
        mQuestionArrayList.add(Question(R.string.question_africa, false))
        mQuestionArrayList.add(Question(R.string.question_americas, true))
        mQuestionArrayList.add(Question(R.string.question_asia, true))

        checkPrevStatus()

        trueButton.setOnClickListener {
            checkAnswer(true)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
        }
        nextButton.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionArrayList.size
            updateQuestion()
            checkPrevStatus()
        }

        prevButton.setOnClickListener {
            mCurrentIndex = (mCurrentIndex - 1) % mQuestionArrayList.size
            updateQuestion()
            checkPrevStatus()
        }
    }

    private fun updateQuestion() {
        val questionTextResId = mQuestionArrayList[mCurrentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = mQuestionArrayList[mCurrentIndex].answer
        val messageId = if (correctAnswer == userAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show()
    }

    private fun checkPrevStatus() {
        if (mCurrentIndex == 0) {
            prevButton.visibility = View.GONE
        } else {
            prevButton.visibility = View.VISIBLE
        }
    }
}