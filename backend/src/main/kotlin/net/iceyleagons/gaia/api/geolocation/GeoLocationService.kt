package net.iceyleagons.gaia.api.geolocation

/**
 * The service used for locating a user just by their IP alone.
 *
 * @author TOTHTOMI, Gabe
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
interface GeoLocationService {

    /**
     * **APPROXIMATELY!!!** Locates an user based on their current IP address.
     *
     * @param ip the ip address that we want to look up.
     * @return a [GeoLocationResponse] containing all the fields.
     */
    fun locateIP(ip: String): GeoLocationResponse

    /**
     * Whether the geolocation can provide accuracy within a street at max a suburb.
     * If the location is accurate enough, the software will scan for public wifis with the provided data.
     */
    val accurateEnoughForPublicScanning: Boolean

}