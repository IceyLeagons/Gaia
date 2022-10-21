package net.iceyleagons.junction.api.wifi.responses

import kotlinx.serialization.Serializable

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@Serializable
data class WigleResponse(
    val success: Boolean,
    val totalResults: Int,
    val first: Int,
    val last: Int,
    val resultCount: Int,
    val results: Array<WigleNetwork>
) {
    @Serializable
    data class WigleNetwork(
        val trilat: Float,
        val trilong: Float,
        val ssid: String,
        val qos: Int,
        val type: String,
        val freenet: String
    )
}