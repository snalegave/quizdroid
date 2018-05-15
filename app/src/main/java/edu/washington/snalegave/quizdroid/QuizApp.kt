package edu.washington.snalegave.quizdroid

import android.app.Application
import android.os.Bundle
import android.os.Environment
import android.util.Log
import java.io.Serializable
import android.os.Environment.getExternalStorageDirectory
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.File
import java.io.IOException


class QuizApp: Application() {
    lateinit  var dataObject: QuizData

    override fun onCreate() {
        super.onCreate()
        Log.i("QuizApp", "OnCreate launched")


            val sdcard = Environment.getExternalStorageDirectory()
            val file = File(sdcard, "/quizdroid/questions.json")

            // FOR the TA: The JSON file should be pushed to /sdcard/ using adb for this to work.
            // EXTRA CREDIT: I created my own JSON file, you can find it here: https://api.myjson.com/bins/laz42
            // You can save it to the same directory as myQuestions.json to access the file.

            dataObject = if(file.exists()){
                val bufferedReader: BufferedReader = file.bufferedReader()
                val inputString = bufferedReader.use { it.readText() }
                JsonQuizData(inputString)
            } else {
                CreateQuizData()
            }
    }
    fun getTopics():List<Topic> {
        return dataObject.getTopicsList()
    }

}

data class Question(val questionText: String, val option1: String, val option2: String, val option3: String, val option4: String,
                    val correctOption: Int) : Serializable


data class Topic(val topicName: String, val description: String, val questions: List<Question>) : Serializable

interface QuizData {
    fun  getTopicsList(): List<Topic>
}

class JsonQuizData(JsonFile:String) : QuizData{
    private val topics= mutableListOf<Topic>()
    init {
        try {
            val topicsInJSON = JSONArray(JsonFile)
            for (i in 0..topicsInJSON.length()-1){
                val topicData = topicsInJSON.getJSONObject(i)
                val title = topicData.getString("title")
                val desc = topicData.getString("desc")

                val topicQuestions = topicData.getJSONArray("questions")
                val listOfQuestions = mutableListOf<Question>()

                for(j in 0..topicQuestions.length()-1){

                    val questionData = topicQuestions.getJSONObject(j)
                    val question = questionData.getString("text")
                    val answer = questionData.getInt("answer")
                    val options = questionData.getJSONArray("answers")

                    val option1 = options.getString(0)
                    val option2 = options.getString(1)
                    val option3 = options.getString(2)
                    val option4 = options.getString(3)

                    listOfQuestions.add(Question(question, option1, option2, option3, option4, answer))

                }
                topics.add(Topic(title, desc, listOfQuestions))
            }
        }catch(e: JSONException){
            e.printStackTrace()
        }
    }
    override fun getTopicsList(): List<Topic>{
        return topics
    }
}

class CreateQuizData: QuizData {


    val mathQuestions: List<Question> = listOf(
            Question("What is perimeter of a triangle with the sides of length 3,4 and 5?", "10", "11", "12", "13", 3),
            Question("What is the square root of 196?", "7", "12", "14", "18", 3))
    val physicsQuestions: List<Question> = listOf(
            Question("At what temperature Fahrenheit does water boil?", "212", "204", "198", "235", 1),
            Question("Energy is measured in what unit of measurement?", "Watts", "Calories", "Joules", "Degrees", 3))
    val marvelQuestions: List<Question> = listOf(
            Question("How many siblings does Thor have?", "0", "1", "2", "3", 3),
            Question("What is DeadPool's superpower?", "Regeneration", "Sarcasm", "laser eyes", "Loud Gas", 1))

    val math = Topic("Math", "here goes the long description for math and math related problems", mathQuestions)
    val physics = Topic("Physics",  "How much do you know about how stuff works?", physicsQuestions)
    val marvel = Topic("Marvel", "Find out how well you know your favorite characters in the MCU!", marvelQuestions)

    private var topics: List<Topic> = listOf(math, physics, marvel)


    override fun getTopicsList(): List<Topic>{
        return topics
    }

}