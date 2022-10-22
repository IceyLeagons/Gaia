package net.iceyleagons.gaia.api.geocoding.responses

import com.google.maps.model.GeocodingResult
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


/**
 * Responsible for holding the geo coding (query --> lat, long) response data.
 *
 * @author TOTHTOMI, Gabe
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@Serializable
data class GeoCodingResponse(
    /**
     * API id of the place.
     *
     * When using maps.co as the backend, the type of this field is Int, and when Google is used, it is a String.
     */
    @Serializable(with = AnySerializer::class) @SerialName("place_id") val placeId: Any,

    /**
     * Latitude coordinate of the place.
     */
    @SerialName("lat") val latitude: Double,

    /**
     * Longitude coordinate of the place.
     */
    @SerialName("lon") val longitude: Double,

    /**
     * Display-name of the place.
     */
    @SerialName("display_name") val displayName: String,

    /**
     * Type of the place (for instance; residential)
     */
    val type: String,

    /**
     * The subtype for the provided [type].
     */
    @SerialName("class") val `class`: String,
) {
    /**
     * A quick little helper object so Kotlinx can serialize Any according to our needs.
     *
     * @author Gabe
     */
    internal object AnySerializer : KSerializer<Any> {
        override val descriptor = PrimitiveSerialDescriptor("Any", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): Any {
            return try {
                decoder.decodeInt()
            } catch (e: Exception) {
                decoder.decodeString()
            }
        }

        override fun serialize(encoder: Encoder, value: Any) {
            TODO("Not yet implemented")
        }
    }

    companion object {
        /**
         * Instantiates a [GeoCodingResponse] from a Google Maps API response.
         */
        fun fromGoogleResponse(geocodingResult: GeocodingResult): GeoCodingResponse {
            val placeId = geocodingResult.placeId
            val latLng = geocodingResult.geometry.location
            val displayName = geocodingResult.formattedAddress

            return GeoCodingResponse(
                placeId,
                latLng.lat,
                latLng.lng,
                displayName,
                "google_type",
                geocodingResult.types[0].name
            )
        }
    }
}