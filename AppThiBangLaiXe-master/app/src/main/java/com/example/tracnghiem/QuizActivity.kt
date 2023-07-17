package com.example.tracnghiem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuizActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionAdapter: QuestionAdapter
    private lateinit var btnNext: Button

    private lateinit var watcher: TextView

    private var TIME: Long = 300000 //ms ==300s= 5p

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        questionAdapter = QuestionAdapter(ListQuestion.listQuestion)
        btnNext = findViewById(R.id.btn_next)
        watcher = findViewById(R.id.watcher1)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = questionAdapter

        btnNext.setOnClickListener {
            startActivity(Intent(this, ResultActivity::class.java))
        }

        val timer = object: CountDownTimer(TIME, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                runOnUiThread {
                    watcher.text = millisecondsToTime(millisUntilFinished)
                }
            }

            override fun onFinish() {
                goToResult()
            }
        }
        timer.start()

    }

    fun goToResult() {
        startActivity(Intent(this, ResultActivity::class.java))
    }
    private fun millisecondsToTime(milliseconds: Long): String? {
        val minutes = milliseconds / 1000 / 60
        val seconds = milliseconds / 1000 % 60
        val secondsStr = java.lang.Long.toString(seconds)
        val secs: String
        secs = if (secondsStr.length >= 2) {
            secondsStr.substring(0, 2)
        } else {
            "0$secondsStr"
        }
        return "$minutes:$secs"
    }
}