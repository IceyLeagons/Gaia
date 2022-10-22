package net.iceyleagons.gaia.services

import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import net.iceyleagons.gaia.api.GoogleAPIService
import net.iceyleagons.gaia.api.geocoding.responses.GeoCodingResponse
import net.iceyleagons.gaia.exception.GeoCodingException
import org.springframework.stereotype.Service

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@Service
class GoogleAPIServiceImpl(private val geoApiContext: GeoApiContext) : GoogleAPIService {
    override fun locateAddress(query: String): GeoCodingResponse {
        // Since Google's data is totally different in structure, we will only use this to code, not to reverse-code.
        // This is good enough as a fallback, as the other service can handle coordinates pretty well, but Google's API can handle
        // querying better.

        // Because this is the fallback, we want to throw error here
        try {
            val result = GeocodingApi.geocode(geoApiContext, query).address(query).await()
            return GeoCodingResponse.fromGoogleResponse(
                result[0] ?: throw GeoCodingException("Unable to geo-code address.")
            )
        } catch (e: Exception) {
            throw GeoCodingException("Unable to geo-code address.")
        }
    }
}