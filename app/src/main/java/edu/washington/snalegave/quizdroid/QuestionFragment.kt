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
private const val ARG_PARAM3 = "startingPosition"
private const val ARG_PARAM4 = "quizQuestionArray"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [QuestionFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [QuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class QuestionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var questionNumber: Int? = null
    private var numOfCorrectAnswers: Int? = null
    private var startingPosition: Int? = null
    private var questionArray: Array<String>? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questionNumber = it.getInt(ARG_PARAM1)
            numOfCorrectAnswers = it.getInt(ARG_PARAM2)
            startingPosition = it.getInt(ARG_PARAM3)
            questionArray = it.getStringArray(ARG_PARAM4)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_question, container, false)

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

        question.text = questionArray!![startingPosition!!]
        qNumber.text = "Question " + (questionNumber!!+1)
        radioButton1.text = questionArray!![startingPosition!! + 1]
        radioButton2.text = questionArray!![startingPosition!! + 2]
        radioButton3.text = questionArray!![startingPosition!! + 3]
        radioButton4.text = questionArray!![startingPosition!! + 4]

        submitSelection.setOnClickListener {
            val selectedButtonID = radioGroupOptions.checkedRadioButtonId
            val selectedOption = view.findViewById<RadioButton>(selectedButtonID).text
            val correct = selectedOption==(questionArray!![startingPosition!! + 5])
            val newCorrectAnswers = if (correct) numOfCorrectAnswers!! + 1 else numOfCorrectAnswers!!

            val fragmentManager3 = fragmentManager
            val fragmentTransaction = fragmentManager3?.beginTransaction()

            val bundle = Bundle()
            bundle.putInt("questionNumber", questionNumber!!)
            bundle.putInt("numOfCorrectAnswers", newCorrectAnswers)
            bundle.putInt("startingPosition", startingPosition!!+6)
            bundle.putStringArray("quizQuestionArray", questionArray!!)
            bundle.putString("answerSelected", selectedOption.toString())
            bundle.putString("correctAnswer", questionArray!![startingPosition!! + 5] )


            val answer = AnswerFragment()
            answer.arguments = bundle

            fragmentTransaction?.add(R.id.fragment_container, answer)
            fragmentTransaction?.commit()



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
         * @return A new instance of fragment QuestionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                QuestionFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
