package net.iceyleagons.gaia.api.wifi.responses

import kotlinx.serialization.Serializable

/**
 * A serializable data class containing information about a hotspot retrieved from Wifimap.io.
 *
 * @author TOTHTOMI, Gabe
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@Serializable
data class WifimapHotspot(
    /**
     * The address of the hotspot.
     *
     * **This is optional, and most of the time is not provided.**
     */
    val address: String = "No address provided",

    /**
     * The Wifimap.io assigned uuid of this hotspot.
     */
    val uuid: String,

    /**
     * The latitude that this wifi was discovered on.
     */
    val lat: Double,

    /**
     * The longitude that this wifi was discovered on.
     */
    val lng: Double,

    /**
     * The name of this wifi.
     *
     * *Weird, probably not the same as [ssid].*
     */
    val name: String,

    /**
     * The SSID of this wifi network.
     */
    val ssid: String,

    /**
     * Wifimap.io assigned category identifier.
     *
     * *Yet again a weird field, considering that it sometimes does not even exist.*
     */
    val category: String = "00000000000000"
)