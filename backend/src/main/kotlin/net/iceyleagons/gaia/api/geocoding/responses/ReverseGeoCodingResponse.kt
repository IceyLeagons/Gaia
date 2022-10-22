package net.iceyleagons.gaia.api.geocoding.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Responsible for holding the reverse geo coding (lat, long --> addr) response data.
 *
 * @author TOTHTOMI, Gabe
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
     * @author TOTHTOMI, Gabe
     * @version 1.0.0
     * @since Oct. 21, 2022
     */
    @Serializable
    data class Address(
        /**
         * The road that this coordinate was pointing to.
         *
         * On Google, this is defined as: **street_address**
         */
        val road: String = "",

        /**
         * The neighbourhood that this coordinate was pointing to.
         *
         * On Google, this is defined as: **neighborhood**
         */
        val neighbourhood: String = "",

        /**
         * The suburb that this coordinate was pointing to.
         */
        val suburb: String = "",

        /**
         * The state that this coordinate was pointing to.
         *
         * On Google, this is defined as: **administrative_area_level_1**
         */
        val state: String = "",

        /**
         * The city that this coordinate was pointing to.
         */
        val city: String = "",

        /**
         * The village that this coordinate was pointing to.
         */
        val village: String = "",

        /**
         * The municipality that this coordinate was pointing to.
         */
        val municipality: String = "",

        /**
         * The county that this coordinate was pointing to.
         *
         * On Google, this is defined as: **administrative_area_level_2**
         */
        val county: String = "",

        /**
         * The region that this coordinate was pointing to.
         */
        val region: String = "",

        /**
         * The postal code that this coordinate was pointing to.
         *
         * On Google, this is defined as: **postal_code**
         */
        val postcode: String = "",

        /**
         * The county that this coordinate was pointing to.
         */
        val country: String = "",

        /**
         * The ISO-3166 country code of the country that this coordinate was pointing to.
         */
        @SerialName("country_code") val countryCode: String = ""
    )
}