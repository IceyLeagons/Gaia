package net.iceyleagons.junction.services

import net.iceyleagons.junction.api.wifi.PublicWifiService
import java.math.BigDecimal

class PublicWifiServiceImpl : PublicWifiService {

    override fun findPublicWifisAround(lat: BigDecimal, long: BigDecimal, maxRange: BigDecimal) {
        TODO("Not yet implemented")
    }

    private fun getWigleWifis(): List<WigleData> = emptyList()

    @Serializable
    data class WigleData(
            val success: Boolean,
            val totalResults: Int,
            val first: Int,
            val last: Int,
            val resultCount: Int,
            val results: Array<WigleWifi>
    ) {
        @Serializable
        data class WigleNetwork(
            val 
        )
    }

}