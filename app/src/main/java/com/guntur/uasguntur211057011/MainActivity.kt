package com.guntur.uasguntur211057011

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.guntur.uasguntur211057011.R
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var rvuser :ListView
    var arrayListDetails:ArrayList<Model> = ArrayList()
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvuser= findViewById<ListView>(R.id.listView)
        run("https://jsonplaceholder.typicode.com/todos")
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
}