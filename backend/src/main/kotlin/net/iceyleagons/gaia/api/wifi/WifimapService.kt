package net.iceyleagons.gaia.api.wifi

import net.iceyleagons.gaia.api.wifi.responses.WifimapHotspot

/**
 * Searches for publicly - and freely - accessible wifi networks in a given area.
 *
 * @author TOTHTOMI, Gabe
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
interface WifimapService {

    /**
     * Searches for all the public wifis around provided [latitude] and [longitude] in the range of [maxRange] in the city [cityName].
     *
     * @param latitude the latitude of the center of this search.
     * @param longitude the longitude of the center of this search.
     * @param maxRange the range in which we search for networks.
     * @param cityName the name of the city in which the coordinates provided reside.
     * @return all the hotspots in the provided search range.
     */
    fun findPublicWifisAround(
        latitude: Double,
        longitude: Double,
        maxRange: Double,
        cityName: String
    ): Array<WifimapHotspot>

}