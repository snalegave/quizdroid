package edu.washington.snalegave.quizdroid

import android.app.Application
import android.os.Bundle
import android.util.Log
import java.io.Serializable

class QuizApp: Application() {
    var dataObject: QuizData?=null

    override fun onCreate() {
        super.onCreate()
        Log.i("QuizApp", "OnCreate launched")
        dataObject =  CreateQuizData()
    }
    fun getTopics():List<Topic> {
        return dataObject!!.getTopicsList()
    }

}

data class Question(val questionText: String, val option1: String, val option2: String, val option3: String, val option4: String,
                    val correctOption: Int) : Serializable


data class Topic(val topicName: String, val ShortDescription: String, val longDescription: String, val questions: List<Question>) : Serializable

interface QuizData {
    fun  getTopicsList(): List<Topic>
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
            Question("What is DeadPool's superpower ", "Regeneration", "Sarcasm", "laser eyes", "Loud Gas", 1))

    val math = Topic("Math", "math is number stuff", "here goes the long description for math and math related problems", mathQuestions)
    val physics = Topic("Physics", "how does stuff work?", "here goes the long description for physics, which is still a mystery to me", physicsQuestions)
    val marvel = Topic("Marvel", "questions about the MCU", "Find out how well you know your favorite characters in the MCU!", marvelQuestions)

    private var topics: List<Topic> = listOf(math, physics, marvel)


    override fun getTopicsList(): List<Topic>{
        return topics
    }

}