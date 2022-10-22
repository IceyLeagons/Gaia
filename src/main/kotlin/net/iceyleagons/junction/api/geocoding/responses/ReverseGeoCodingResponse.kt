package net.iceyleagons.junction.api.geocoding.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Responsible for holding the reverse geo coding (lat, long --> addr) response data.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@Serializable
data class ReverseGeoCodingResponse(
    /**
     * API id of the place
     */
    @SerialName("place_id") val placeId: Int,

    /**
     * Display-name of the place
     */
    @SerialName("display_name") val displayName: String,

    /**
     * Address of the place
     */
    val address: Address
) {

    /**
     * Responsible for holding the information about an address.
     *
     * @author TOTHTOMI
     * @version 1.0.0
     * @since Oct. 21, 2022
     */
    @Serializable
    data class Address(
        val road: String = "", // on google: street_address
        val neighbourhood: String = "", //on google: neighborhood
        val suburb: String = "", //
        val state: String = "", // on google: administrative_area_level_1
        val city: String = "",
        val village: String = "",
        val municipality: String = "",
        val county: String = "", // on google: administrative_area_level_2
        val region: String = "", //
        val postcode: String = "", // on google: postal_code
        val country: String = "", // on google: country
        @SerialName("country_code") val countryCode: String = ""
    )
}