package net.iceyleagons.junction.api.geolocation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@Serializable
data class GeoLocationResponse(
    val ip: String,
    @SerialName("country") val countryCode: String,
    @SerialName("state_prov") val province: String,
    val city: String,
    @SerialName("isp_name") val ispName: String,
    val latitude: Double,
    val longitude: Double,
    @SerialName("web_proxy") val webProxy: Boolean = false,
    @SerialName("public_proxy") val publicProxy: Boolean = false,
    val vpn: Boolean,
    val tor: Boolean
) {
    val proxied
        get() = webProxy || publicProxy
}