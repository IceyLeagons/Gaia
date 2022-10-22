package net.iceyleagons.junction.api.geocoding.responses

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
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@Serializable
data class GeoCodingResponse(
    /**
     * API id of the place
     */
    @Serializable(with = AnySerializer::class) @SerialName("place_id") val placeId: Any,

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

    @SerialName("class") val clazz: String,

    /**
     * Type of the place (ex. residential)
     */
    val type: String,
) {
    object AnySerializer : KSerializer<Any> {
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