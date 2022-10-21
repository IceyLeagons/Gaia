package net.iceyleagons.junction.api.wifi

import net.iceyleagons.junction.api.wifi.responses.WigleResponse

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
interface WigleService {

    fun findPublicWifisAround(latitude: Double, longitude: Double, range: Double): Array<WigleResponse.WigleNetwork>

}