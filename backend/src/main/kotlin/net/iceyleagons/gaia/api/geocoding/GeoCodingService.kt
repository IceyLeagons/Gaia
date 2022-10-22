package net.iceyleagons.gaia.api.geocoding

import net.iceyleagons.gaia.api.geocoding.responses.GeoCodingResponse
import net.iceyleagons.gaia.api.geocoding.responses.ReverseGeoCodingResponse
import net.iceyleagons.gaia.api.geolocation.GeoLocationResponse

/**
 * This class handles all the geocoding-related tasks, be it geocoding, and reverse-geocoding.
 *
 * @author TOTHTOMI, Gabe
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
interface GeoCodingService {

    /**
     * Decodes a provided geographical coordinate to a human-readable object.
     *
     * @param lat the latitude of the location.
     * @param long the longitude of the location.
     * @return a [ReverseGeoCodingResponse] object, containing all the information accessible from that coordinate.
     */
    fun decode(lat: Double, long: Double): ReverseGeoCodingResponse

    /**
     * Reverse-geocodes a [GeoLocationResponse] into a human-readable object.
     *
     * @param location the result of a geolocation lookup.
     * @return a [ReverseGeoCodingResponse] object, containing all the information accessible from that coordinate.
     */
    fun decode(location: GeoLocationResponse): ReverseGeoCodingResponse

    /**
     * Searches for the information that is obtainable from a street address.
     *
     * @param query the address we want to convert into an object.
     * @return a [ReverseGeoCodingResponse] object, containing all the information accessible from that address.
     */
    fun codeToReverse(query: String): ReverseGeoCodingResponse

    /**
     * Retrieves the geographical coordinates of a street address.
     *
     * @param query the address we want to get the coordinates of.
     * @return a [GeoCodingResponse] object, containing the longitude and latitude of that address.
     */
    fun code(query: String): GeoCodingResponse
}