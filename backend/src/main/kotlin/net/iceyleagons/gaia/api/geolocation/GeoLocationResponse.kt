package net.iceyleagons.gaia.api.geolocation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A serializable data class containing all the information retrieved from a call to the SEON servers.
 *
 * @author TOTHTOMI, Gabe
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@Serializable
data class GeoLocationResponse(
    /**
     * The IP address of the fellow.
     */
    val ip: String,

    /**
     * The ISO-3166 country code that the user is currently staying in.
     */
    @SerialName("country") val countryCode: String,

    /**
     * The province in which the user is currently staying in.
     */
    @SerialName("state_prov") val province: String,

    /**
     * The city that the user is - *probably* - currently residing in.
     */
    val city: String,

    /**
     * The name of the users **I**nternet **S**ervice **P**rovider.
     */
    @SerialName("isp_name") val ispName: String,

    /**
     * The approximate latitude of the user.
     */
    val latitude: Double,

    /**
     * The approximate longitude of the user.
     */
    val longitude: Double,

    /**
     * Whether the user is currently connected through a web proxy.
     */
    @SerialName("web_proxy") val webProxy: Boolean = false,

    /**
     * Whether the user is currently connected through a public proxy.
     */
    @SerialName("public_proxy") val publicProxy: Boolean = false,

    /**
     * Whether the user is currently using a VPN.
     */
    val vpn: Boolean,

    /**
     * Whether the user is - most probably - using Tor.
     */
    val tor: Boolean
) {
    /**
     * An alias to [webProxy] || [publicProxy].
     */
    val proxied
        get() = webProxy || publicProxy
}