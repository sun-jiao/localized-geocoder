package sunjiao.nominatim

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.webkit.WebSettings
import androidx.annotation.NonNull
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okio.IOException
import org.json.JSONException
import org.json.JSONObject

/**
 *
 * @author sun-jiao (孙娇）
 *
 */

class Nominatim//according to Nominatim ToS, user agent is necessary
    (
    @NonNull private var latitude: Float,
    @NonNull private var longitude: Float,
    @NonNull private val language: String,
    @NonNull private val useragent: String)
{
    private val TAG =  "Nominatim"
    private fun getJSON() : JSONObject? {
        val client : OkHttpClient = OkHttpClient()
        val builder = "https://nominatim.openstreetmap.org/reverse?".toHttpUrlOrNull()?.newBuilder()
        builder?.addQueryParameter("format", "json")
        builder?.addQueryParameter("lat", latitude.toString())
        builder?.addQueryParameter("lon", longitude.toString())
        builder?.addQueryParameter("zoom", "18")
        builder?.addQueryParameter("addressdetails", "1")
        builder?.addQueryParameter("accept-language", language)
        val request = Request.Builder()
            .url(builder.toString())
            .addHeader("User-Agent", useragent)
            .addHeader("Referer", "https://github.com/sun-jiao/LocalizedGeocoder/")
            .build()
        val call: Call = client.newCall(request)
        var jsonObject : JSONObject? = JSONObject()
        var response0: Response? = null
        call.enqueue(object : Callback {

            override fun onFailure(call: Call, e: java.io.IOException) {
                jsonObject = null
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    response0 = response
                     jsonObject  = JSONObject(response.body!!.string())
                } catch (e: JSONException) {}
            }
        })
        Log.i(TAG, request.toString())
        Log.i(TAG, response0.toString())
        if(jsonObject != null) {
            if (jsonObject!!.isNull("error")){
                return jsonObject
            } else
                return null
        } else
            return null

    }

    fun getAddress() : Address? {
        val json : JSONObject? = getJSON()
        Log.i(TAG, json.toString())
        if (json != null && !json.isNull("address") && !json.isNull("display_name") ){
            return Address( json.getJSONObject("address"), latitude, longitude, json.getString("display_name"))
        } else
            return null
    }
}