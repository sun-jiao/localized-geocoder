package sunjiao.geocoder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import sunjiao.localizer.CNLocalizer
import sunjiao.nominatim.Nominatim

/**
 *
 * @author sun-jiao (孙娇）
 *
 */

class MainActivity : AppCompatActivity() {
    var useragent : String = ""
    var resultText : TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        geocode_button.setOnClickListener { geoCode() }
        useragent = "SunjiaoLocalizedGeocoderSample / 1.0.0"
        val uri = Uri.parse("https://github.com/sun-jiao/LocalizedGeocoder")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        github.setOnClickListener { startActivity(intent) }
        resultText = findViewById<TextView>(R.id.result)
       /*
        * Test value and excepted result.
        * test value: lat=29.49594 lon=95.46306 : 排墨扎线, 冷多, 达木珞巴族乡, 墨脱县, 林芝市, 西藏自治区, 中国
        * test value: "lat":"24.4514469","lon":"118.3493971": 紫玄宮, 610, 環島北路二段, 后盤山, 后盤村, 金寧鄉, 金門縣, 福建省, 中国
        * test value: "lat":"25.04057705","lon":"121.52014940586452": 國立臺灣大學醫學院附設醫院, 1, 常德街, 東門里, 城內, 中正區, 臺北市, 台湾省, 中国
        * test value: 20.69861,116.72887 : 東沙漁民服務站, 东沙群岛, 碣石镇, 陆丰市, 汕尾市, 广东省, 中国
        * test value: "lat":"27.42466","lon":"89.03055": Charit (恰尔塘), 鲁林地区, 仁青岗村, 下亚东乡, 亚东县, 日喀则市, 西藏自治区, 中国
        * test value: "lat":"27.29852","lon":"88.93961": 洞朗草场主营地, 洞朗公路, 细姆, 洞朗地区, 下亚东乡, 亚东县, 日喀则市, 西藏自治区, 中国
        * test value: lat = 27.42256, Lon = 88.94463: 夏日, 仁青岗村, 亚东县, 日喀则市, 西藏自治区, 中国
        * test value: lat=32.0561, lon=78.6116: 巨哇、曲惹地区, 楚鲁松杰乡, 札达县, 阿里地区, 西藏自治区, 中国
        * test value: "lat":"27.5836" , "lon":"91.8678": 达旺, 错那县, 山南市, 西藏自治区, 中国
        */
    }

    fun geoCode(){
        val nominatim : Nominatim = Nominatim(latitude_text.text.toString().toFloat(),longitude_text.text.toString().toFloat(),"zh-cn", useragent)
        val address = nominatim.getAddress()
        val str : String
        if (address != null){
            Log.i(nominatim.TAG, "success 1")
           str =  CNLocalizer(address).getLocalizedAddress()
            Log.i(nominatim.TAG , str)
            resultText?.setText(str)
        }

    }
}
