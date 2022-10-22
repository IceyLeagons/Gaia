package net.iceyleagons.gaia.api

import net.iceyleagons.gaia.api.geocoding.responses.GeoCodingResponse

/**
 * Handles Google Maps API related requests.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 22, 2022
 */
interface GoogleAPIService {

    /**
     * Locates an address and returns a [GeoCodingResponse] through the use of [GeoCodingResponse.fromGoogleResponse].
     *
     * @param query the address which we want to geocode.
     * @return a [GeoCodingResponse] containing all the information accessible through the use of the Google API, with [GeoCodingResponse.type] set as "google_type"
     */
    fun locateAddress(query: String): GeoCodingResponse

}