package edu.washington.snalegave.quizdroid

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "questionNumber"
private const val ARG_PARAM2 = "numOfCorrectAnswers"
private const val ARG_PARAM3 = "topic"


class QuestionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var questionNumber: Int? = null
    private var numOfCorrectAnswers: Int? = null
    private lateinit var topic: Topic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questionNumber = it.getInt(ARG_PARAM1)
            numOfCorrectAnswers = it.getInt(ARG_PARAM2)
            topic = it.getSerializable(ARG_PARAM3) as Topic

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        val question = view.findViewById<TextView>(R.id.displayQuestion)
        val qNumber = view.findViewById<TextView>(R.id.qNumber)
        val radioGroupOptions = view.findViewById<RadioGroup>(R.id.radioGroupOptions)
        val radioButton1 = view.findViewById<RadioButton>(R.id.option1)
        val radioButton2 = view.findViewById<RadioButton>(R.id.option2)
        val radioButton3 = view.findViewById<RadioButton>(R.id.option3)
        val radioButton4 = view.findViewById<RadioButton>(R.id.option4)
        val submitSelection = view.findViewById<Button>(R.id.submitSelection)

        submitSelection.isEnabled = false
        radioGroupOptions.setOnCheckedChangeListener { _, _ ->
            submitSelection.isEnabled = true
        }
        val q: Question= topic.questions[questionNumber!!]
        question.text = q.questionText
        qNumber.text = "Question " + (questionNumber!! + 1)
        radioButton1.text = q.option1
        radioButton2.text = q.option2
        radioButton3.text = q.option3
        radioButton4.text = q.option4

        val x = "option"+q.correctOption
        submitSelection.setOnClickListener {
            val selectedButtonID = radioGroupOptions.checkedRadioButtonId
            val selectedOption = view.findViewById<RadioButton>(selectedButtonID).text
            val correct = selectedOption == (q.option1)    // find a way to get the correct option here
            val newCorrectAnswers = if (correct) numOfCorrectAnswers!! + 1 else numOfCorrectAnswers!!

            val fragmentManager3 = fragmentManager
            val fragmentTransaction = fragmentManager3?.beginTransaction()

            val bundle = Bundle()
            bundle.putInt("questionNumber", questionNumber!!)
            bundle.putInt("numOfCorrectAnswers", newCorrectAnswers)
            bundle.putSerializable("topic", topic)
            bundle.putString("answerSelected", selectedOption.toString())
            bundle.putString("correctAnswer", q.option1)   // again, add the right answer logic here


            val answer = AnswerFragment()
            answer.arguments = bundle

            fragmentTransaction?.replace(R.id.fragment_container, answer)
            fragmentTransaction?.commit()


        }

        return view
    }
}
