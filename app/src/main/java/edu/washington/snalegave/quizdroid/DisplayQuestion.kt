package edu.washington.snalegave.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class DisplayQuestion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_question)
        val questionNumber = intent.getIntExtra("questionNumber", 0)
        val numOfCorrectAnswers = intent.getIntExtra("numOfCorrectAnswers", 0)
        val startingPosition = intent.getIntExtra("startingPosition", 0)
        val questionArray = intent.getStringArrayExtra("questions")

        val question = findViewById<TextView>(R.id.displayQuestion)
        val qNumber = findViewById<TextView>(R.id.qNumber)
        val radioGroupOptions = findViewById<RadioGroup>(R.id.radioGroupOptions)
        val radioButton1 = findViewById<RadioButton>(R.id.option1)
        val radioButton2 = findViewById<RadioButton>(R.id.option2)
        val radioButton3 = findViewById<RadioButton>(R.id.option3)
        val radioButton4 = findViewById<RadioButton>(R.id.option4)
        val submitSelection = findViewById<Button>(R.id.submitSelection)

        submitSelection.isEnabled = false
        radioGroupOptions.setOnCheckedChangeListener { _, _ ->
            submitSelection.isEnabled = true
        }

        question.text = questionArray[startingPosition]
        qNumber.text = "Question " + (questionNumber+1)
        radioButton1.text = questionArray[startingPosition + 1]
        radioButton2.text = questionArray[startingPosition + 2]
        radioButton3.text = questionArray[startingPosition + 3]
        radioButton4.text = questionArray[startingPosition + 4]

        submitSelection.setOnClickListener {
            val selectedButtonID = radioGroupOptions.checkedRadioButtonId
            val selectedOption = findViewById<RadioButton>(selectedButtonID).text
            val correct = selectedOption==(questionArray[startingPosition + 5])
            val newCorrectAnswers = if (correct) numOfCorrectAnswers + 1 else numOfCorrectAnswers

            val intent= Intent(this, DisplayAnswer::class.java).apply{
                putExtra("questionNumber", questionNumber)
                putExtra("questions", questionArray)
                putExtra("answerSelected", selectedOption)
                putExtra("correctAnswer",questionArray[startingPosition + 5] )
                putExtra("numOfCorrectAnswers",newCorrectAnswers )
                putExtra("startingPosition", startingPosition + 6)
            }
            startActivity(intent)

        }
    }
}
