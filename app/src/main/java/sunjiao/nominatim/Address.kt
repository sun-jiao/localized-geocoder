package sunjiao.nominatim

import androidx.annotation.NonNull
import androidx.annotation.Nullable

//you can add more parameters if useful in your country.
class Address(
    @Nullable private var  building: String,
    @Nullable private var  house_number: String,
    @Nullable private var  road: String,
    @Nullable private var  address29 : String,
    @Nullable private var  allotments : String,
    @Nullable private var  isolated_dwelling : String,     //1-2 households
    @Nullable private var  hamlet: String,                 //small village with 100-200 people
    @Nullable private var  locality : String,
    @Nullable private var  farm : String,
    @Nullable private var  school: String,
    @Nullable private var  neighbourhood : String,         //有时候是小区，有时候是社区
    @Nullable private var  suburb : String,                //社区
    @Nullable private var  city_district: String,
    @Nullable private var  town: String,                   //有时候是街道
    @Nullable private var  city : String,                  //有时候是街道, 有时候是区
    @Nullable private var  county: String,                 //区、县
    @Nullable private var  region: String,                 //市
    @Nullable private var  state_district: String,         //州辖区，相当于地区级
    @Nullable private var  state: String,
    @NonNull  private var  country: String,
    @NonNull  private var  country_code: String
) {

}