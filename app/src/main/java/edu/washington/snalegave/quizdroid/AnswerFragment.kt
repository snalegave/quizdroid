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
private const val ARG_PARAM3 = "startingPosition"
private const val ARG_PARAM4 = "questionArray"
private const val ARG_PARAM5 = "answerSelected"
private const val ARG_PARAM6 = "correctAnswer"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AnswerFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AnswerFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AnswerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var questionNumber: Int? = null
    private var numOfCorrectAnswers: Int? = null
    private var startingPosition: Int? = null
    private var questionArray: Array<String>? = null
    private var answerSelected: String? = null
    private var correctAnswer: String? = null

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questionNumber = it.getInt(ARG_PARAM1)
            numOfCorrectAnswers = it.getInt(ARG_PARAM2)
            startingPosition = it.getInt(ARG_PARAM3)
            questionArray = it.getStringArray(ARG_PARAM4)
            answerSelected = it.getString(ARG_PARAM5)
            correctAnswer = it.getString(ARG_PARAM6)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_answer, container, false)
        val totalQuestions = questionArray!!.size/6

        val userOptionText = view.findViewById<TextView>(R.id.userOption)
        val correctAnswerText = view.findViewById<TextView>(R.id.correctAnswer)
        val quizStatus = view.findViewById<TextView>(R.id.quizStatus)
        val nextStep = view.findViewById<Button>(R.id.nextStep)
        val status = view.findViewById<TextView>(R.id.answerStatus)

        userOptionText.text = "You selected: " + answerSelected
        correctAnswerText.text = "Correct answer: " + correctAnswer
        quizStatus.text = "You have " + numOfCorrectAnswers +" out of " + totalQuestions + " correct"

        status.text = if(answerSelected==correctAnswer) "CORRECT" else "WRONG"

        val lastQuestion = (questionNumber!!+1 == totalQuestions)

        if (lastQuestion) {
            nextStep.text = "Finish"
        } else {
            nextStep.text = "Next"
        }
        nextStep.setOnClickListener {
            if (!lastQuestion){ // go to next question
                val changeFragToQuestion = fragmentManager
                val fragmentTransaction = changeFragToQuestion?.beginTransaction()

                val bundle = Bundle()
                bundle.putInt("questionNumber", questionNumber!!+1)
                bundle.putInt("numOfCorrectAnswers", numOfCorrectAnswers!!)
                bundle.putInt("startingPosition", startingPosition!!)
                bundle.putStringArray ("quizQuestionArray", questionArray)

                val question = QuestionFragment()
                question.arguments = bundle

                fragmentTransaction?.add(R.id.fragment_container, question)
                fragmentTransaction?.commit()

            } else { // go home
                val i = Intent(view.context, MainActivity::class.java)
                startActivity(i)
            }

        }
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AnswerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                AnswerFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
