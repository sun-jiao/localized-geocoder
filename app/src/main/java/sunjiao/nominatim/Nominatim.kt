package sunjiao.nominatim

import android.util.Log
import androidx.annotation.NonNull
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONException
import org.json.JSONObject
import java.net.SocketTimeoutException
import javax.net.ssl.SSLException


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
    val TAG =  "Nominatim"

    @Throws(KotlinNullPointerException::class ,  IllegalStateException::class , RuntimeException::class, SSLException::class)
    private fun getJSON() : String? {
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
        var response: Response? = null
        val thread : Thread = Thread(Runnable {
            try {
                response = call.execute()
            } catch (e : SocketTimeoutException){
                e.printStackTrace()
            }
        })
        thread.start()
        thread.join()
        Log.i(TAG, request.toString())
        if (response == null)
            return null
        else {
            Log.i(TAG, response.toString())
            if (response!!.body == null)
                return null
            else{
                val str = response?.body!!.string()
                Log.i(TAG, str)
                return str
            }
        }
    }

    fun getAddress() : Address? {
        val str = getJSON()
        if (str != null){
            val json : JSONObject? = JSONObject(str)
            Log.i(TAG, json.toString())
            if (json != null && !json.isNull("address") && !json.isNull("display_name") ){
                return Address( json.getJSONObject("address"), latitude, longitude, json.getString("display_name"))
            } else
                return null
        } else
            return null
    }
}