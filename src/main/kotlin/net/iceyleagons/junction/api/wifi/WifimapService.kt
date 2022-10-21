package net.iceyleagons.junction.api.wifi

import net.iceyleagons.junction.api.wifi.responses.WifimapHotspot

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
interface WifimapService {

    fun findPublicWifisAround(latitude: Double, longitude: Double, maxRange: Double, cityName: String): Array<WifimapHotspot>

}