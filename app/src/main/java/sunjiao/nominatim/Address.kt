package sunjiao.nominatim

import androidx.annotation.NonNull
import androidx.annotation.Nullable

/**
 *
 * @author Sun Jiao
 *
 */

//you can add more parameters if useful in your country.
class Address(
    @Nullable private val  building: String,
    @Nullable private val  house_number: String,
    @Nullable private val  road: String,
    @Nullable private val  address29 : String,
    @Nullable private val  allotments : String,
    @Nullable private val  isolated_dwelling : String,     //1-2 households
    @Nullable private val  hamlet: String,                 //small village with 100-200 people
    @Nullable private val  locality : String,
    @Nullable private val  farm : String,
    @Nullable private val  school: String,
    @Nullable private val  neighbourhood : String,         //有时候是小区，有时候是社区
    @Nullable private val  suburb : String,                //社区
    @Nullable private val  city_district: String,
    @Nullable private val  town: String,                   //有时候是街道
    @Nullable private val  city : String,                  //有时候是街道, 有时候是区
    @Nullable private val  county: String,                 //区、县
    @Nullable private val  region: String,                 //市
    @Nullable private val  state_district: String,         //州辖区，相当于地区级
    @Nullable private val  state: String,
    @NonNull  private val  country: String,
    @NonNull  private val  country_code: String,

    @NonNull  private val  latitude :  Float,
    @NonNull  private val  longitude : Float
) {

}