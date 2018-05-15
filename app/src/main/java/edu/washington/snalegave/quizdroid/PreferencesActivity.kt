package edu.washington.snalegave.quizdroid

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.*
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.FileReader
import java.io.IOException
import java.net.URL
import android.os.AsyncTask.execute
import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.os.Build
import android.os.Environment
import android.util.Log
import java.io.File
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.provider.Settings
import kotlinx.android.synthetic.main.activity_preferences.*


class PreferencesActivity : AppCompatActivity() {


    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val url = intent.getStringExtra("userURL")
            val interval = intent.getStringExtra("userInterval")
            Log.i("PreferencesCtivity", "entered the download part")
            Toast.makeText(this@PreferencesActivity, "downloading from: " + url + " every " + interval + " minutes!", Toast.LENGTH_SHORT).show()
            Log.i("PreferencesCtivity", "downloading from: " + url + " every " + interval + " minutes!")

            if(isConnectedToInternet()){
                val fileName = "questions.json"
                val completePath = Environment.getExternalStorageDirectory().toString() + "/quizdroid/" + fileName
                val file = File(completePath)
                val imageUri = Uri.fromFile(file)


                val request = DownloadManager.Request(Uri.parse(url))
                request.setDescription("Starting Download of your data set")
                request.setTitle("questions.json")

                if(fileExists("/quizdroid/" + fileName)){
                    deleteFile("/quizdroid/" + fileName)
                }
                request.setDestinationUri(imageUri)

                // get download service and enqueue file
                val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                manager.enqueue(request)

            }

        }
    }

    private fun fileExists(filename: String): Boolean {

        val file1 = File(Environment.getExternalStorageDirectory().absolutePath + filename)
        return file1.exists()


    }

    override fun deleteFile(filename: String): Boolean {

        val file1 = File(Environment.getExternalStorageDirectory().absolutePath + filename)
        return file1.delete()


    }

    private lateinit var alarmIntent: PendingIntent

    private var alarmStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        setSupportActionBar(findViewById(R.id.my_toolbar))

        val urlText = findViewById<EditText>(R.id.urlPref)
        val intervalText =findViewById<EditText>(R.id.downloadRate)
        val startButton = findViewById<Button>(R.id.startDownload)
        val goBack = findViewById<Button>(R.id.backButton)

        startButton.setOnClickListener{
            var connectedToInternet = isConnectedToInternet()
            var validFields = true
            var validUrl: Boolean
            val userURL = urlText.text.toString()
            val userInterval = intervalText.text.toString()
            val alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            this.registerReceiver(receiver, IntentFilter("receiveData"))

            val intervalCheck = try {
                userInterval.toInt() > 0
            }catch (e: NumberFormatException){
                false
            }


            if (alarmStatus){
                startButton.text = "Start"
                Log.i("preferenceActivity", "alarm is turned off")

                alarmIntent.cancel()
                alarmMgr.cancel(alarmIntent)
                alarmStatus= false
            }else if (!userURL.isEmpty() && intervalCheck && connectedToInternet){
                startButton.text = "Stop"
                alarmStatus= true

                Log.i("preferenceActivity", "start new alarm")

                val url = try {
                    validUrl = true
                    URL(userURL)
                } catch(e: IOException) {
                    validUrl = false
                    e.printStackTrace()
                }


                if (validUrl){
                    Log.i("preferenceActivity", "url valid")


                    val intent = Intent().apply {
                        putExtra("userURL", userURL)
                        putExtra("userInterval", userInterval)
                        action="receiveData"
                    }
                    alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
                    alarmMgr.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + userInterval.toInt()* 1000 * 60, userInterval.toLong()* 1000 * 60, alarmIntent)


                } else {
                    Log.i("preferenceActivity", "url not valid")
                    Toast.makeText(this, "invalid URL pls fix", Toast.LENGTH_SHORT).show()
                }
            }
        }

        backButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun isConnectedToInternet(): Boolean {
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val networkAvailable =  activeNetwork != null && activeNetwork.isConnectedOrConnecting
        if(networkAvailable){
            return true
        }else {
            val airplaneModeStatus = Settings.Global.getInt(getApplicationContext().getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0
            if (airplaneModeStatus){
                Toast.makeText(this, "Turn off airplane mode to download stuff", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Connect to the internet to download stuff", Toast.LENGTH_SHORT).show();
            }
            return false
        }

    }
}
