package com.example.secondactivitydemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

private const val EXTRA_ANSWER_IS_TRUE = "com.example.secondactivitydemo.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "com.example.secondactivitydemo.answer_shown"

class CheatActivity : AppCompatActivity() {
    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private var answerIsTrue = false

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        quizViewModel.isAnswerTrue = answerIsTrue

        showAnswerButton.setOnClickListener {
            quizViewModel.isCheating = true
            setAnswer()
            setAnswerShownResult(true)
        }
        if( quizViewModel.isCheating){
            setAnswer()
            setAnswerShownResult(true)
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply() {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    private fun setAnswer(){
        val answerText = when {
            quizViewModel.isAnswerTrue -> R.string.btn_true
            else -> R.string.btn_false
        }
        answerTextView.setText(answerText)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}