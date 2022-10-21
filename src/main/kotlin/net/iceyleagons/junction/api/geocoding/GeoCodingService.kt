package net.iceyleagons.junction.api.geocoding

import java.math.BigDecimal

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
interface GeoCodingService {

    fun decode(lat: BigDecimal, long: BigDecimal): ReverseGeoCodingResponse

    fun code(query: String): GeoCodingResponse
}