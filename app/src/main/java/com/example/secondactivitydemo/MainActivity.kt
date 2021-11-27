package com.example.secondactivitydemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.mCurrentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        prevButton = findViewById(R.id.prev_button)
        cheatButton = findViewById(R.id.cheat_button)

        updateQuestion()
        checkPrevStatus()

        trueButton.setOnClickListener {
            checkAnswer(true)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
        }
        cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            checkPrevStatus()
            quizViewModel.isCheating = false
        }
        prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
            checkPrevStatus()
        }
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageId = when {
            quizViewModel.isCheating -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show()
    }

    private fun checkPrevStatus() {
        if (quizViewModel.mCurrentIndex == 0) {
            prevButton.visibility = View.GONE
        } else {
            prevButton.visibility = View.VISIBLE
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.mCurrentIndex)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheating = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
            Log.e("Nishikant", "user cheating : " + quizViewModel.isCheating)
        }
    }
}