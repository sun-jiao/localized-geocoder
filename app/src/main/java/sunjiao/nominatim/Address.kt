package sunjiao.nominatim

import androidx.annotation.NonNull
import androidx.annotation.Nullable

/**
 *
 * @author sun-jiao (孙娇）
 *
 */

//you can add more parameters if useful in your country.
class Address(
    @Nullable  val  building: String,
    @Nullable  val  house_number: String,
    @Nullable  val  road: String,
    @Nullable  val  address29 : String,
    @Nullable  val  allotments : String,
    @Nullable  val  isolated_dwelling : String,     //1-2 households
    @Nullable  val  hamlet: String,                 //small village with 100-200 people
    @Nullable  val  locality : String,
    @Nullable  val  farm : String,
    @Nullable  val  school: String,
    @Nullable  val  neighbourhood : String,
    @Nullable  val  suburb : String,
    @Nullable  val  city_district: String,
    @Nullable  val  town: String,
    @Nullable  val  city : String,
    @Nullable  val  county: String,
    @Nullable  val  region: String,
    @Nullable  val  state_district: String,
    @Nullable  val  state: String,
    @NonNull   val  country: String,
    @NonNull   val  country_code: String,

    @NonNull   val  latitude :  Float,
    @NonNull   val  longitude : Float,
    @NonNull   val  display_name : String
) {

}