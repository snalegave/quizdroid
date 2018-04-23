package edu.washington.snalegave.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DisplayAnswer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_answer)

        val questionNumber = intent.getIntExtra("questionNumber", 0)
        val numOfCorrectAnswers = intent.getIntExtra("numOfCorrectAnswers", 0)
        val startingPosition = intent.getIntExtra("startingPosition", 0)
        val questionArray = intent.getStringArrayExtra("questions")
        val answerSelected = intent.getStringExtra("answerSelected")
        val correctAnswer = intent.getStringExtra("correctAnswer")

        val userOptionText = findViewById<TextView>(R.id.userOption)
        val correctAnswerText = findViewById<TextView>(R.id.correctAnswer)
        val quizStatus = findViewById<TextView>(R.id.quizStatus)
        val nextStep = findViewById<Button>(R.id.nextStep)

        userOptionText.text = "You selected: " + answerSelected
        correctAnswerText.text = "Correct answer: " + correctAnswer
        quizStatus.text = "You have " + numOfCorrectAnswers +" out of " + 2 + "correct"     // 2 is hardcoded, replace with a variable for the num of question in the quiz

        nextStep.setOnClickListener {
            val intent= Intent(this, DisplayQuestion::class.java).apply{
                putExtra("questions", questionArray)
                putExtra("startingPosition", startingPosition)
                putExtra("questionNumber", questionNumber)
                putExtra("numOfCorrectAnswers", numOfCorrectAnswers)
            }
            startActivity(intent)
        }

    }
}
