package net.iceyleagons.junction.api.geocoding

import net.iceyleagons.junction.api.geocoding.responses.GeoCodingResponse
import net.iceyleagons.junction.api.geocoding.responses.ReverseGeoCodingResponse
import net.iceyleagons.junction.api.geolocation.GeoLocationResponse

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
interface GeoCodingService {

    fun decode(lat: Double, long: Double): ReverseGeoCodingResponse

    fun decode(location: GeoLocationResponse): ReverseGeoCodingResponse

    fun codeToReverse(query: String): ReverseGeoCodingResponse

    fun code(query: String): GeoCodingResponse
}