package edu.washington.snalegave.quizdroid

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "quitTopic"
private const val ARG_PARAM2 = "quitDesc"
private const val ARG_PARAM3 = "quizQuestionArray"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TopicDescFrag.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TopicDescFrag.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TopicDescFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: Array<String>? = null

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getStringArray(ARG_PARAM3)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_topic_desc, container, false)

        val numOfQuestions = param3!!.size/6



        val title = view.findViewById<TextView>(R.id.quizTitle)
        val desc = view.findViewById<TextView>(R.id.quizDescription)
        val questions = view.findViewById<TextView>(R.id.quizQuestions)
        val beginButton = view.findViewById<Button>(R.id.quizStart)

        Log.i("MyActivity", param1)
        Log.i("MyActivity", param2)
        Log.i("MyActivity", param3!!.size.toString())

        title.text = param1
        desc.text = param2
        questions.text = "There are " + numOfQuestions.toString() + " questions in this array"


        beginButton.setOnClickListener{
            val fragmentManager2 = fragmentManager
            val fragmentTransaction = fragmentManager2?.beginTransaction()

            val bundle = Bundle()
            bundle.putInt("questionNumber", 0)
            bundle.putInt("numOfCorrectAnswers", 0)
            bundle.putInt("startingPosition", 0)
            bundle.putStringArray ("quizQuestionArray", param3)

            val question = QuestionFragment()
            question.arguments = bundle

            fragmentTransaction?.add(R.id.fragment_container, question)
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
         * @return A new instance of fragment TopicDescFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TopicDescFrag().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                        putStringArray(ARG_PARAM3, param3)
                    }
                }
    }
}
