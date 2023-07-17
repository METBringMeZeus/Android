package com.example.tracnghiem

data class Question(
    val id: String,
    val title: String,
    val a1: String,
    val a2: String,
    val a3: String,
    val a4: String,
    var user_answer: String,
    val correct_answer: String
)