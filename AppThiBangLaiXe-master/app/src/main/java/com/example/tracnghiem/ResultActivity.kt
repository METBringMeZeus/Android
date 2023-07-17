package com.example.tracnghiem

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResultActivity : AppCompatActivity() {
    private var wrong : Int = 0
    private var listWrong: ArrayList<Question> = arrayListOf()

    private lateinit var correctResult: TextView
    private lateinit var wrongResult: TextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var questionAdapter: QuestionAdapterResult
    private lateinit var btnAgain : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        correctResult = findViewById(R.id.correct_result)
        wrongResult = findViewById(R.id.wrong_result)

        questionAdapter = QuestionAdapterResult(applicationContext, listWrong)

        btnAgain = findViewById(R.id.btn_again)
        recyclerView = findViewById(R.id.recycler_view_result)
        recyclerView.adapter = questionAdapter

        btnAgain.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        checkResult()
        updateUI()
    }

    private fun checkResult() {
        ListQuestion.listAnswer.forEach {
            if (it.user_answer != it.correct_answer) {
                wrong++
                listWrong.add(it)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateUI() {
        listWrong
        correctResult.text = (ListQuestion.listQuestion.size - wrong).toString()
        wrongResult.text = wrong.toString()
        questionAdapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
    }
}