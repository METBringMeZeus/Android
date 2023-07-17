package com.example.tracnghiem

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestionAdapter(private val listQuestion: ArrayList<Question>) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title_quiz)

        val answerOneCheckBox:  CheckBox = view.findViewById(R.id.answer_one_check_box)
        val answerOneTitle: TextView = view.findViewById(R.id.answer_one_title)

        val answerTwoCheckBox:  CheckBox = view.findViewById(R.id.answer_two_check_box)
        val answerTwoTitle: TextView = view.findViewById(R.id.answer_two_title)

        val answerThreeCheckBox:  CheckBox = view.findViewById(R.id.answer_three_check_box)
        val answerThreeTitle: TextView = view.findViewById(R.id.answer_three_title)

        val answerFourCheckBox:  CheckBox = view.findViewById(R.id.answer_four_check_box)
        val answerFourTitle: TextView = view.findViewById(R.id.answer_four_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.quiz_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = listQuestion[position].title
        holder.answerOneTitle.text = listQuestion[position].a1
        holder.answerTwoTitle.text = listQuestion[position].a2
        holder.answerThreeTitle.text = listQuestion[position].a3
        holder.answerFourTitle.text = listQuestion[position].a4


        holder.answerOneCheckBox.setOnClickListener {
            run {
                holder.answerTwoCheckBox.isChecked = false
                holder.answerThreeCheckBox.isChecked = false
                holder.answerFourCheckBox.isChecked = false
            }
            updateListAnswer(listQuestion[position].id, "a")
        }

        holder.answerTwoCheckBox.setOnClickListener {
            run {
                holder.answerOneCheckBox.isChecked = false
                holder.answerThreeCheckBox.isChecked = false
                holder.answerFourCheckBox.isChecked = false
            }
            updateListAnswer(listQuestion[position].id, "b")
        }

        holder.answerThreeCheckBox.setOnClickListener {
            run {
                holder.answerTwoCheckBox.isChecked = false
                holder.answerOneCheckBox.isChecked = false
                holder.answerFourCheckBox.isChecked = false
            }
            updateListAnswer(listQuestion[position].id, "c")
        }

        holder.answerFourCheckBox.setOnClickListener {
            run {
                holder.answerTwoCheckBox.isChecked = false
                holder.answerThreeCheckBox.isChecked = false
                holder.answerOneCheckBox.isChecked = false
            }
            updateListAnswer(listQuestion[position].id, "d")
        }
    }

    private fun updateListAnswer(id: String, answer: String) {
        ListQuestion.listAnswer.forEach {
            if (it.id == id) {
                it.user_answer = answer
                return
            }
        }
    }


    override fun getItemCount(): Int {
        return listQuestion.size
    }
}