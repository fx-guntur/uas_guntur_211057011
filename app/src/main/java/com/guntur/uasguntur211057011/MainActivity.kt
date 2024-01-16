package com.guntur.uasguntur211057011

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.guntur.uasguntur211057011.R
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var rvuser :ListView
    var arrayListDetails:ArrayList<Model> = ArrayList()
    private val client = OkHttpClient()
    private lateinit var notificationManager: NotificationManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvuser= findViewById<ListView>(R.id.listView)
        notificationManager = NotificationManagerCompat.from(this)
        run("https://jsonplaceholder.typicode.com/todos")

        rvuser.setOnItemClickListener { _, _, position, _ ->
            val selectedModel = arrayListDetails[position]
            sendNotification(selectedModel.id, selectedModel.title)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "channelId"
            val channelName = "Channel Name"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun run(url: String) {
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val strResponse = response.body()?.string()
                    val jsonArray = JSONArray(strResponse)

                    for (i in 0 until jsonArray.length()) {
                        val jsonObjectDetail: JSONObject = jsonArray.getJSONObject(i)
                        val model = Model()
                        model.userId = jsonObjectDetail.getString("userId")
                        model.id = jsonObjectDetail.getString("id")
                        model.title = jsonObjectDetail.getString("title")
                        model.completed = jsonObjectDetail.getBoolean("completed")
                        arrayListDetails.add(model)
                    }

                    runOnUiThread {
                        val objectAdapter = CustomAdapter(applicationContext, arrayListDetails)
                        rvuser.adapter = objectAdapter
                    }
                }
            }
        }
        )
    }
    @SuppressLint("MissingPermission")
    private fun sendNotification(id: String, title: String) {
        val notificationId = 1

        val builder = NotificationCompat.Builder(this, "channelId")
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("Item Dipilih")
            .setContentText("ID: $id, Judul: $title")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        notificationManager.notify(notificationId, builder.build())
    }
}