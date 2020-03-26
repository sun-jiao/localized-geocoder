package sunjiao.nominatim

import androidx.annotation.NonNull
import org.json.JSONObject

/**
 *
 * @author sun-jiao (孙娇）
 *
 */

//you can add more parameters if useful in your country.
class Address(
    @NonNull   val  ranks : JSONObject,
    @NonNull   val  latitude :  Float,
    @NonNull   val  longitude : Float,
    @NonNull   val  display_name : String
) {

}