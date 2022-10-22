package net.iceyleagons.junction.api

import net.iceyleagons.junction.api.geocoding.responses.GeoCodingResponse

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
interface GoogleAPIService {

    fun locateAddress(query: String): GeoCodingResponse

}