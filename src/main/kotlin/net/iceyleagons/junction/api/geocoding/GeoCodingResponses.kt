package net.iceyleagons.junction.api.geocoding

import org.json.JSONArray
import org.json.JSONObject
import java.math.BigDecimal

/**
 * Responsible for holding the geo coding (query --> lat, long) response data.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
data class GeoCodingResponse(
    /**
     * API id of the place
     */
    val placeId: Int,

    /**
     * Latitude coordinate of the place
     */
    val lat: BigDecimal,

    /**
     * Longitude coordinate of the place
     */
    val long: BigDecimal,

    /**
     * Display-name of the place
     */
    val displayName: String,

    /**
     * Classname of the place (tbh I have no idea, what is this)
     */
    val className: String,

    /**
     * Type of the place (ex. residential)
     */
    val type: String,

    /**
     * Importance of the place
     */
    val importance: Float
) {
    companion object {
        fun fromJson(rawData: String): GeoCodingResponse {
            val json = JSONArray(rawData)[0] as JSONObject
            return GeoCodingResponse(
                json["place_id"] as Int,
                BigDecimal(json["lat"] as String),
                BigDecimal(json["lon"] as String),
                json["display_name"] as String,
                json["class"] as String,
                json["type"] as String,
                (json["importance"] as BigDecimal).toFloat()
            )
        }
    }
}

/**
 * Responsible for holding the reverse geo coding (lat, long --> addr) response data.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
data class ReverseGeoCodingResponse(
    /**
     * API id of the place
     */
    val placeId: Int,

    /**
     * Display-name of the place
     */
    val displayName: String,

    /**
     * Address of the place
     */
    val address: Address
) {
    companion object {
        fun fromJson(rawData: String): ReverseGeoCodingResponse {
            val json = JSONObject(rawData)
            return ReverseGeoCodingResponse(
                json["place_id"] as Int,
                json["display_name"] as String,
                Address.fromJson(json["address"] as JSONObject)
            )
        }
    }
}

/**
 * Responsible for holding the information about an address.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
data class Address(
    val road: String,
    val suburb: String,
    val city: String,
    val municipality: String,
    val county: String,
    val region: String,
    val postcode: String,
    val country: String,
    val countyCode: String
) {
    companion object {
        fun fromJson(json: JSONObject): Address {
            return Address(
                json["road"] as String,
                json["suburb"] as String,
                json["city"] as String,
                json["municipality"] as String,
                json["county"] as String,
                json["region"] as String,
                json["postcode"] as String,
                json["country"] as String,
                json["country_code"] as String
            )
        }
    }
}