package net.iceyleagons.junction.api.wifi

import java.math.BigDecimal

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
interface PublicWifiService {

    fun findPublicWifisAround(lat: BigDecimal, long: BigDecimal, maxRange: BigDecimal)

}