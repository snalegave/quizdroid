package edu.washington.snalegave.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class TopicDescription : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_description)

        val quizTopic = intent.getStringExtra("topic")
        val quizDesc = intent.getStringExtra("description")
        val quizQuestionArray= intent.getStringArrayExtra("questions")
        val numOfQuestions = quizQuestionArray.size/6


        val title = findViewById<TextView>(R.id.quizTitle)
        val desc = findViewById<TextView>(R.id.quizDescription)
        val questions = findViewById<TextView>(R.id.quizQuestions)
        val beginButton = findViewById<Button>(R.id.quizStart)

        Log.i("MyActivity", quizTopic)
        Log.i("MyActivity", quizDesc)
        Log.i("MyActivity", quizQuestionArray.size.toString())

        title.text = quizTopic
        desc.text = quizDesc
        questions.text = "There are " + numOfQuestions.toString() + " questions in this array"

        beginButton.setOnClickListener{
            val intent= Intent(this, DisplayQuestion::class.java).apply{
                putExtra("questions", quizQuestionArray)
                putExtra("startingPosition", 0)
            }
            startActivity(intent)
        }



    }
}
