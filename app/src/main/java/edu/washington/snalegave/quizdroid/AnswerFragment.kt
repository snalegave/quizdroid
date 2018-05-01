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
import android.widget.TextView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "questionNumber"
private const val ARG_PARAM2 = "numOfCorrectAnswers"
private const val ARG_PARAM3 = "topic"
private const val ARG_PARAM4 = "answerSelected"
private const val ARG_PARAM5 = "correctAnswer"

class AnswerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var questionNumber: Int? = null
    private var numOfCorrectAnswers: Int? = null
    private lateinit var topic: Topic
    private lateinit var answerSelected: String
    private lateinit var correctAnswer: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questionNumber = it.getInt(ARG_PARAM1)
            numOfCorrectAnswers = it.getInt(ARG_PARAM2)
            topic = it.getSerializable(ARG_PARAM3) as Topic
            answerSelected = it.getString(ARG_PARAM4)
            correctAnswer = it.getString(ARG_PARAM5)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_answer, container, false)
        val totalQuestions = topic.questions.size

        val userOptionText = view.findViewById<TextView>(R.id.userOption)
        val correctAnswerText = view.findViewById<TextView>(R.id.correctAnswer)
        val quizStatus = view.findViewById<TextView>(R.id.quizStatus)
        val nextStep = view.findViewById<Button>(R.id.nextStep)
        val status = view.findViewById<TextView>(R.id.answerStatus)

        userOptionText.text = "You selected: " + answerSelected
        correctAnswerText.text = "Correct answer: " + correctAnswer
        quizStatus.text = "You have " + numOfCorrectAnswers + " out of " + totalQuestions + " correct"      // change second arg to total questions

        status.text = if (answerSelected == correctAnswer) "CORRECT" else "WRONG"

        val lastQuestion = (questionNumber!! == totalQuestions - 1)

        if (lastQuestion) {
            nextStep.text = "Finish"
        } else {
            nextStep.text = "Next"
        }
        nextStep.setOnClickListener {
            if (!lastQuestion) { // go to next question
                val changeFragToQuestion = fragmentManager
                val fragmentTransaction = changeFragToQuestion?.beginTransaction()

                val bundle = Bundle()
                bundle.putInt("questionNumber", questionNumber!! + 1)
                bundle.putInt("numOfCorrectAnswers", numOfCorrectAnswers!!)
                bundle.putSerializable("topic", topic)

                val question = QuestionFragment()
                question.arguments = bundle

                fragmentTransaction?.replace(R.id.fragment_container, question)
                fragmentTransaction?.commit()

            } else { // go home
                val i = Intent(view.context, MainActivity::class.java)
                startActivity(i)
            }

        }
        return view
    }
}