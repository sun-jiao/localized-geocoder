package sunjiao.localizer

import org.osmdroid.util.GeoPoint

class Rectangle(
    val east : Double,
    val west : Double,
    val south : Double,
    val north : Double
) {
    fun isIn (testPoint : GeoPoint) : Boolean{
        return (testPoint.latitude > south )&&(testPoint.latitude < north )&&(testPoint.longitude > west)&&(testPoint.longitude < east)
    }
}