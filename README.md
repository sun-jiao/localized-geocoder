# LocalizedGeocoder
[![](https://jitpack.io/v/sun-jiao/localized-geocoder.svg)](https://jitpack.io/#sun-jiao/localized-geocoder)

Localized Geocoder is an Android library based on Nominatim, can be used to solve the boundary disputes. <br/>
This library is developed by *Sun Jiao*, Github username: @sun-jiao .<br/>
Welcome to contribute for your country.<br/>
 ![image](https://raw.githubusercontent.com/sun-jiao/localized-geocoder/master/Screenshot_20200329-164202.png)

# How to use
## How to include it

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency
Add it in your app moudle build.gradle file.

	dependencies {
	        implementation 'com.github.sun-jiao:localized-geocoder:0.2.0'
	}

## How to get geocode result

* Instance of **Nominatim** with **latitude**, **longitude**, **language**, **useragent** (necessary, because of the Nominatim ToS).
* If you want to use another geocode service, or you have deployed nominatim on your on server, you can add the **base url** of it following the **useragent** as the last parameter in string.
* Get Address by **getAddress**, you can get the json address, the whole display address from it. If you don't want a localized result, that's all.
* If not, you need to continue to do following me.
* Next, you should use a localizer, its name usually is the ISO country code + "Localizer". 

### kotlin

	val nominatim : Nominatim = Nominatim(latitude_text.text.toString().toFloat(),longitude_text.text.toString().toFloat(),"zh-cn", useragent, myNominatimServer /*optional*/ ) 
        val address = nominatim.getAddress()
        val str : String
        if (address != null){
           str =  CNLocalizer(address).getLocalizedAddress()
           // the str is the final result, do anything you want with it.
        }

### java
	
	Nominatim nominatim = new Nominatim(latitude_text.text.toString().toFloat(),longitude_text.text.toString().toFloat(),"zh-cn", useragent, myNominatimServer /*optional*/ );
	String address = nominatim.getAddress();
	String str = new String;
	if (address != null){
           str =  (new CNLocalizer(address)).getLocalizedAddress();
           // the str is the final result, do anything you want with it.
        }
