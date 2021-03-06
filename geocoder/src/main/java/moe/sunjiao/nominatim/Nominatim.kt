package moe.sunjiao.nominatim

import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import okhttp3.Call
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import org.osmdroid.util.GeoPoint
import moe.sunjiao.nominatim.Address
import java.net.SocketTimeoutException
import javax.net.ssl.SSLException


/**
 *
 * @author sun-jiao (孙娇）
 *
 */

class Nominatim//according to Nominatim ToS, user agent is necessary

    (
    @NonNull private val latitude:  Double,
    @NonNull private val longitude: Double,
    @NonNull private val language: String,
    @NonNull private val useragent: String,
    @Nullable private val baseUrl : String = "https://nominatim.openstreetmap.org/reverse?")
{
    val TAG =  "Nominatim"

    constructor(latitude: Double, longitude: Double, language:String, useragent: String)
            : this(latitude, longitude, language, useragent, "https://nominatim.openstreetmap.org/reverse?")

    constructor(geoPoint: GeoPoint, language:String, useragent: String, baseUrl : String)
            : this(geoPoint.latitude, geoPoint.longitude, language, useragent, baseUrl)

    constructor(geoPoint: GeoPoint, language:String, useragent: String)
            : this(geoPoint.latitude, geoPoint.longitude, language, useragent)

    constructor(latitudeF: Float, longitudeF: Float, language:String, useragent: String, baseUrl : String)
            : this(latitudeF.toDouble(), longitudeF.toDouble(), language, useragent, baseUrl)

    constructor(latitudeF: Float, longitudeF: Float, language:String, useragent: String)
            : this(latitudeF.toDouble(), longitudeF.toDouble(), language, useragent)

    private fun getJSON() : String? {
        val client : OkHttpClient = OkHttpClient()
        val builder = baseUrl.toHttpUrlOrNull()?.newBuilder()
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
            } catch (e : Exception){
                e.printStackTrace()
            }
        })
        thread.start()
        thread.join()
        Log.i(TAG, request.toString())
        return if (response == null)
            null
        else {
            Log.i(TAG, response.toString())
            if (response!!.body == null)
                null
            else{
                val str = response?.body!!.string()
                Log.i(TAG, str)
                str
            }
        }
    }

    fun getAddress() : Address? {
        val str = getJSON()
        if (str != null){
            val json : JSONObject? = JSONObject(str)
            Log.i(TAG, json.toString())
            if (json != null && !json.isNull("address") && !json.isNull("display_name") ){
                return Address(
                    json.getJSONObject("address"),
                    latitude,
                    longitude,
                    json.getString("display_name")
                )
            } else
                return null
        } else
            return null
    }
}