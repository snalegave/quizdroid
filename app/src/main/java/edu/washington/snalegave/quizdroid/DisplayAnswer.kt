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
        val totalQuestions = questionArray.size/6

        val userOptionText = findViewById<TextView>(R.id.userOption)
        val correctAnswerText = findViewById<TextView>(R.id.correctAnswer)
        val quizStatus = findViewById<TextView>(R.id.quizStatus)
        val nextStep = findViewById<Button>(R.id.nextStep)
        val status = findViewById<TextView>(R.id.answerStatus)

        userOptionText.text = "You selected: " + answerSelected
        correctAnswerText.text = "Correct answer: " + correctAnswer
        quizStatus.text = "You have " + numOfCorrectAnswers +" out of " + totalQuestions + " correct"

        status.text = if(answerSelected==correctAnswer) "CORRECT" else "WRONG"

        val lastQuestion = (questionNumber+1 == totalQuestions)

        if (lastQuestion) {
            nextStep.text = "Finish"
        } else {
            nextStep.text = "Next"
        }

        nextStep.setOnClickListener {
            if (!lastQuestion){
                val intent= Intent(this, DisplayQuestion::class.java).apply{
                    putExtra("questions", questionArray)
                    putExtra("startingPosition", startingPosition)
                    putExtra("questionNumber", questionNumber+1)
                    putExtra("numOfCorrectAnswers", numOfCorrectAnswers)
                }
                startActivity(intent)
            } else {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }

        }

    }
}
