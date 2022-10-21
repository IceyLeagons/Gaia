package net.iceyleagons.junction.api.geolocation

import org.json.JSONObject
import java.math.BigDecimal

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
data class GeoLocationResponse(    val ip: String,
                                   val countyCode: String,
                                   val province: String,
                                   val city: String,
                                   val ispName: String,
                                   val lat: BigDecimal,
                                   val long: BigDecimal,
                                   val proxy: Boolean,
                                   val vpn: Boolean,
                                   val tor: Boolean) {
    companion object {
        fun fromJson(rawJson: String): GeoLocationResponse {
            val json = JSONObject(rawJson)["data"] as JSONObject // TODO error handling
            return GeoLocationResponse(
                json["ip"] as String,
                json["country"] as String,
                json["state_prov"] as String,
                json["city"] as String,
                json["isp_name"] as String,
                json["latitude"] as BigDecimal,
                json["longitude"] as BigDecimal,
                json["web_proxy"] as Boolean || json["public_proxy"] as Boolean,
                json["vpn"] as Boolean,
                json["tor"] as Boolean,
            )
        }
    }
}