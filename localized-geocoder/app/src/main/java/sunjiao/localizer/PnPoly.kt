package sunjiao.localizer

import android.util.Log
import org.osmdroid.util.GeoPoint

/**
 *
 * @author sun-jiao (孙娇）
 * we use pnpoly to determine if the testPoint is in the polygon.
 * true：in the polygon，false：out of the polygon
 *
 */

class PnPoly(val polygon: Array<GeoPoint>) {
    fun isIn ( testPoint : GeoPoint ) : Boolean {
        var z = false
        var j = polygon.size - 1
        for ((i: Int, _:GeoPoint) in polygon.withIndex()){
            if ( ((polygon[i].longitude >testPoint.longitude)
                        != (polygon[j].longitude >testPoint.longitude))
                && (testPoint.latitude
                        < ((polygon[j].latitude -polygon[i].latitude) * (testPoint.longitude -polygon[i].longitude)
                        / (polygon[j].longitude -polygon[i].longitude) + polygon[i].latitude)))
                z = !z
            j = i
        }
        Log.i("pnpoly", "pnpoly")
        return z
    }
}