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
private const val ARG_PARAM1 = "topic"

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
    private lateinit var topic: Topic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            topic = it.getSerializable("topic") as Topic
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_topic_desc, container, false)

        val numOfQuestions = topic.questions.size


        val title = view.findViewById<TextView>(R.id.quizTitle)
        val desc = view.findViewById<TextView>(R.id.quizDescription)
        val questions = view.findViewById<TextView>(R.id.quizQuestions)
        val beginButton = view.findViewById<Button>(R.id.quizStart)


        title.text = topic.topicName
        desc.text = topic.ShortDescription
        questions.text = "There are " + numOfQuestions.toString() + " questions in this array"


        beginButton.setOnClickListener {
            val fragmentManager2 = fragmentManager
            val fragmentTransaction = fragmentManager2?.beginTransaction()

            val bundle = Bundle()
            bundle.putInt("questionNumber", 0)
            bundle.putInt("numOfCorrectAnswers", 0)
            bundle.putSerializable("topic", topic)

            val question = QuestionFragment()
            question.arguments = bundle

            fragmentTransaction?.replace(R.id.fragment_container, question)
            fragmentTransaction?.commit()
        }
        return view
    }
}
