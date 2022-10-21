package net.iceyleagons.junction.api.geolocation

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
interface GeoLocationService {

    fun locateIP(ip: String): GeoLocationResponse

    /**
     * Whether the geolocation can provide accuracy within a street at max a suburb.
     * If the location is accurate enough, the software will scan for public wifis.
     */
    fun accurateEnoughForPublicScanning(): Boolean

}