package sunjiao.geocoder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import sunjiao.nominatim.Nominatim

class MainActivity : AppCompatActivity() {
    var useragent : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        geocode_button.setOnClickListener {geoCode()}
        useragent = WebSettings.getDefaultUserAgent(this)
        /*test value: lat=29.49594 lon=95.46306

         */
    }

    fun geoCode(){
        val nominatim : Nominatim = Nominatim(latitude_text.text.toString().toFloat(),longitude_text.text.toString().toFloat(),"zh-cn", useragent)
        val address = nominatim.getAddress()

    }
}
