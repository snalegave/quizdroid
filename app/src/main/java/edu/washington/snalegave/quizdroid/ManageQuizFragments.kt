package edu.washington.snalegave.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class ManageQuizFragments : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_quiz_fragments)

        val quizTopic = intent.getStringExtra("topic")
        val quizDesc = intent.getStringExtra("description")
        val questionArray= intent.getStringArrayExtra("questions")

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val bundle = Bundle()
        bundle.putString("quizTopic", quizTopic)
        bundle.putString("quizDesc", quizDesc)
        bundle.putStringArray ("questionArray", questionArray)

        val topicDesc = TopicDescFrag()
        topicDesc.arguments = bundle

        fragmentTransaction.add(R.id.fragment_container, topicDesc)
        fragmentTransaction.commit()


    }
}
