package sunjiao.localizer

import org.osmdroid.util.GeoPoint

//2.pnpoly算法，testPoint点平行y轴（或x轴）的，以testPoint点为原点的射线，与多边形的交点数量，交点次数，奇数为真，偶数为假
//true：in the polygon，false：out of the polygon

class PnPoly(area: Array<GeoPoint>) {
    val polygon = area
    fun isIn ( testPoint : GeoPoint ) : Boolean {
        var z = false
        var j = polygon.size - 1
        for ((i: Int, point :GeoPoint) in polygon.withIndex()){
            if ( ((polygon[i].longitude >testPoint.longitude)
                        != (polygon[j].longitude >testPoint.longitude))
                && (testPoint.latitude
                        < ((polygon[j].latitude -polygon[i].latitude) * (testPoint.longitude -polygon[i].longitude)
                        / (polygon[j].longitude -polygon[i].longitude) + polygon[i].latitude)))
                z = !z
            j = i
        }
        return z
    }
}