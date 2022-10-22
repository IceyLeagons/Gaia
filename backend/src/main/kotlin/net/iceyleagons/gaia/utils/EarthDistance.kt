package net.iceyleagons.gaia.utils

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 22, 2022
 */
object EarthDistance {

    private const val R: Int = 6371 // Radius of earth

    /**
     * Calculate distance between two points in latitude and longitude not taking into account height difference.
     * Uses Haversine method as its base.
     *
     * @param lat1 the latitude of the first coordinate
     * @param lng1 the longitude of the first coordinate
     * @param lat2 the latitude of the second coordinate
     * @param lng2 the longitude of the second coordinate
     *
     * @return the distance between the two coordinates in meters
     */
    fun calculate(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
        val latD = Math.toRadians(lat2 - lat1)
        val lonD = Math.toRadians(lng2 - lng1)

        val a =
            sin(latD / 2) * sin(latD / 2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(lonD / 2) * sin(
                lonD / 2
            )
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return R * c * 1000
    }
}