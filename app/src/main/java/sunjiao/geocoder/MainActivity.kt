package sunjiao.geocoder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import sunjiao.nominatim.Nominatim

/**
 *
 * @author sun-jiao (孙娇）
 *
 */

class MainActivity : AppCompatActivity() {
    var useragent : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        geocode_button.setOnClickListener {geoCode()}
        useragent = WebSettings.getDefaultUserAgent(this)
        val uri = Uri.parse("https://github.com/sun-jiao/LocalizedGeocoder")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        github.setOnClickListener { startActivity(intent) }

        /*test value: lat=29.49594 lon=95.46306

         */
    }

    fun geoCode(){
        val nominatim : Nominatim = Nominatim(latitude_text.text.toString().toFloat(),longitude_text.text.toString().toFloat(),"zh-cn", useragent)
        val address = nominatim.getAddress()

    }
}
