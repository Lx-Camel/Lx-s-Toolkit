package com.lx.tools

import android.location.Location
import kotlin.math.*

/**
 * @author liux
 * @date 2022/10/11
 * @desc 计算两个坐标距离工具类
 */
object CoordinateUtil {

    /**
     * 地球半径，单位：米
     */
    private const val EARTH_RADIUS = 6378137

    /**
     * 经纬度转化成弧度
     * @param d  经度/纬度
     * @return  经纬度转化成的弧度
     */
    private fun radian(d: Double): Double {
        return d * Math.PI / 180.0
    }

    /**
     * 返回两个地理坐标之间的距离
     * @param firsLongitude 第一个坐标的经度
     * @param firstLatitude 第一个坐标的纬度
     * @param secondLongitude 第二个坐标的经度
     * @param secondLatitude  第二个坐标的纬度
     * @return 两个坐标之间的距离，单位：米
     */
    fun distance(
        firstLatitude: Double,
        firsLongitude: Double,
        secondLatitude: Double,
        secondLongitude: Double
    ): Double {
        val Lat1 = radian(firstLatitude)
        val Lat2 = radian(secondLatitude)
        val a = Lat1 - Lat2
        val b = radian(firsLongitude) - radian(secondLongitude)
        var cal = 2 * asin(sqrt(sin(a / 2).pow(2.0) + cos(Lat1) * cos(Lat2) * sin(b / 2).pow(2.0)))
        cal *= EARTH_RADIUS
        return cal.roundToInt().toDouble()
    }

    /**
     * 返回两个地理坐标之间的距离
     * @param firstPoint 第一个坐标 例如："23.100919, 113.279868"
     * @param secondPoint 第二个坐标 例如："23.149286, 113.347584" 默认为当前位置
     * @return 两个坐标之间的距离，单位：米
     */
    fun calculateDistance(firstPoint: Location, secondPoint: Location): Double {
        return distance(
            firstPoint.latitude,
            firstPoint.longitude,
            secondPoint.latitude,
            secondPoint.longitude
        )
    }
}