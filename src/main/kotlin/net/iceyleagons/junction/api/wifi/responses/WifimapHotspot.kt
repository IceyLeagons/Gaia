package net.iceyleagons.junction.api.wifi.responses

import kotlinx.serialization.Serializable

@Serializable
data class WifimapHotspot(val address: String = "No address provided", val uuid: String, val lat: Double, val lng: Double, val name: String, val ssid: String, val category: String = "00000000000000")