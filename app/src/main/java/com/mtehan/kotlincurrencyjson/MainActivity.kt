package com.mtehan.kotlincurrencyjson

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnRates = findViewById<Button>(R.id.btnGetRates)
        val usdText = findViewById<TextView>(R.id.usdTxt)
        val tryText = findViewById<TextView>(R.id.tryTxt)
        val chfText = findViewById<TextView>(R.id.chfTxt)
        val osbText = findViewById<TextView>(R.id.osbTxt)
        val cadText = findViewById<TextView>(R.id.cadTxt)

        btnRates.setOnClickListener(View.OnClickListener {
            val downLoad = DownLoad()
            try {
                val url =
                    "http://data.fixer.io/api/latest?access_key=2ffdd025a8470d162cbfdabc361ac16a&format=1"
                downLoad.execute(url)
            } catch (ex: Exception) {
                println(ex.printStackTrace())
            }

        })

    }

    inner class DownLoad : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            var result = ""
            var url: URL
            var httpURLConnection: HttpURLConnection
            try {
                url = URL(params[0])
                httpURLConnection = url.openConnection() as HttpURLConnection
                val inputStream = httpURLConnection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)
                var data = inputStreamReader.read()
                while (data > 0) {
                    val character = data.toChar()
                    result += character
                    data = inputStreamReader.read()
                }
            } catch (ex: Exception) {
                println(ex.printStackTrace())
            }
            return result
        }

        override fun onPostExecute(result: String?) {
            try {
                val jsonObject = JSONObject(result)
                val base = jsonObject.getString("base")
                val rates = jsonObject.getString("rates")
                val jsonObject1 = JSONObject(rates)
                val turk = jsonObject1.getString("TRY")
                val cad = jsonObject1.getString("CAD")
                val gbp = jsonObject1.getString("GBP")
                val usd = jsonObject1.getString("USD")
                val chf = jsonObject1.getString("CHF")

                tryTxt.text="TRY:"+turk
                usdTxt.text="USD:"+usd
                chfTxt.text="CHF:"+chf
                osbTxt.text="OSB:"+gbp
                cadTxt.text="CAD:"+cad

            } catch (ex: Exception) {

            }
            super.onPostExecute(result)
        }
    }
}
