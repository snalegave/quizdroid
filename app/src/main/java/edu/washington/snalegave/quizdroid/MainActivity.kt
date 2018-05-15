package edu.washington.snalegave.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import edu.washington.snalegave.quizdroid.R.string.topic_1

class MainActivity : AppCompatActivity() {

//    private val quizTopics = arrayOf("Math", "Physics", "Marvel Super Heroes")
//    private val quizDescription = arrayOf("Description for Math", "Description for Physics", "Description for Marvel SuperHeroes")
//
//    private val mathSetOfQuestions = arrayOf(
//            "What is perimeter of a triangle with the sides of length 3,4 and 5?", "10", "11", "12", "13", "12",
//            "What is the square root of 196?", "7", "12", "14", "18", "14" )
//
//    private val physicsSetOfQuestions = arrayOf(
//            "At what temperature Fahrenheit does water boil?", "212", "204", "198", "235", "212",
//            "Energy is measured in what unit of measurement?", "Watts", "Calories", "Joules", "Degrees", "Joules")
//
//    private val marvelSetOfQuestions = arrayOf(
//            "How many siblings does Thor have?", "0", "1", "2", "3", "2",
//            "What is DeadPool's superpower ", "Regeneration", "Sarcasm", "laser eyes", "Loud Gas", "Regeneration")
//
//    private val mapOfQuestions = hashMapOf("Math" to mathSetOfQuestions, "Physics" to physicsSetOfQuestions, "Marvel Super Heroes" to marvelSetOfQuestions)


    private lateinit var listView: ListView
    private lateinit var topicList: List<Topic>
    private lateinit var app: QuizApp

    public override fun onResume() {
        super.onResume()

        app = application as QuizApp
        topicList = app.getTopics()


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.my_toolbar))

        app = application as QuizApp
        topicList = app.getTopics()

        var arrayOfTopicNames: Array<String> = Array(topicList.size, { "" })
        for(i in 0..arrayOfTopicNames.size-1){
            arrayOfTopicNames[i]=topicList[i].topicName
        }

        listView = findViewById(R.id.topicListView) as ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayOfTopicNames)

        listView.adapter = adapter


        listView.setOnItemClickListener { _, _, position, _ ->

            val bundle = Bundle()
            bundle.putSerializable("topic", topicList[position])

            val intent= Intent(this, ManageQuizFragments::class.java).apply{
                putExtra("topic", bundle)
            }
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            // User chose the "Settings" item, show the app settings UI...
            Log.i("MainActivity", "Settings was pressed")
            val intent = Intent(this@MainActivity, PreferencesActivity::class.java)
            startActivity(intent)

            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


}
