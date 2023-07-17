package com.example.tracnghiem

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestionAdapterResult(private val context: Context, private val listQuestion: ArrayList<Question>) : RecyclerView.Adapter<QuestionAdapterResult.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title_quiz)

        val answerOneCheckBox: CheckBox = view.findViewById(R.id.answer_one_check_box)
        var answerOneTitle: TextView = view.findViewById(R.id.answer_one_title)

        val answerTwoCheckBox: CheckBox = view.findViewById(R.id.answer_two_check_box)
        var answerTwoTitle: TextView = view.findViewById(R.id.answer_two_title)

        val answerThreeCheckBox: CheckBox = view.findViewById(R.id.answer_three_check_box)
        var answerThreeTitle: TextView = view.findViewById(R.id.answer_three_title)

        val answerFourCheckBox: CheckBox = view.findViewById(R.id.answer_four_check_box)
        var answerFourTitle: TextView = view.findViewById(R.id.answer_four_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.quiz_item_result, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = listQuestion[position].title
        holder.answerOneTitle.text = listQuestion[position].a1
        holder.answerTwoTitle.text = listQuestion[position].a2
        holder.answerThreeTitle.text = listQuestion[position].a3
        holder.answerFourTitle.text = listQuestion[position].a4

        holder.answerOneCheckBox.isEnabled = false
        holder.answerTwoCheckBox.isEnabled = false
        holder.answerThreeCheckBox.isEnabled = false
        holder.answerFourCheckBox.isEnabled = false


        when(listQuestion[position].user_answer) {
            "a" -> holder.answerOneTitle.setTextColor(Color.parseColor("#FF0000"))
            "b" -> holder.answerTwoTitle.setTextColor(Color.parseColor("#FF0000"))
            "c" -> holder.answerThreeTitle.setTextColor(Color.parseColor("#FF0000"))
            "d" -> holder.answerFourTitle.setTextColor(Color.parseColor("#FF0000"))
        }

        when(listQuestion[position].correct_answer) {
            "a" -> holder.answerOneTitle.setTextColor(Color.parseColor("#89FF00"))
            "b" -> holder.answerTwoTitle.setTextColor(Color.parseColor("#89FF00"))
            "b" -> holder.answerTwoTitle.setTextColor(Color.parseColor("#89FF00"))
            "c" -> holder.answerThreeTitle.setTextColor(Color.parseColor("#89FF00"))
            "d" -> holder.answerFourTitle.setTextColor(Color.parseColor("#89FF00"))
        }
    }


    override fun getItemCount(): Int {
        return listQuestion.size
    }
}
