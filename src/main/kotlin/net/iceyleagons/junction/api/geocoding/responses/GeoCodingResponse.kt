package net.iceyleagons.junction.api.geocoding.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Responsible for holding the geo coding (query --> lat, long) response data.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@Serializable
data class GeoCodingResponse(
    /**
     * API id of the place
     */
    @SerialName("place_id") val placeId: Int,

    /**
     * Latitude coordinate of the place
     */
    @SerialName("lat") val latitude: Double,

    /**
     * Longitude coordinate of the place
     */
    @SerialName("lon") val longitude: Double,

    /**
     * Display-name of the place
     */
    @SerialName("display_name") val displayName: String,

    /**
     * Classname of the place (tbh I have no idea, what is this)
     */
    @SerialName("class") val className: String,

    /**
     * Type of the place (ex. residential)
     */
    val type: String,

    /**
     * Importance of the place
     */
    val importance: Float
)