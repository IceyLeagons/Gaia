package net.iceyleagons.junction.detectors.impl

import net.iceyleagons.junction.api.geocoding.GeoCodingService
import net.iceyleagons.junction.api.geolocation.GeoLocationService
import net.iceyleagons.junction.api.wifi.WifimapService
import net.iceyleagons.junction.api.wifi.WigleService
import net.iceyleagons.junction.detectors.Detector
import net.iceyleagons.junction.detectors.Rule
import net.iceyleagons.junction.detectors.UserInput
import net.iceyleagons.junction.utils.Journalist
import org.springframework.beans.factory.BeanFactory
import kotlin.jvm.optionals.getOrNull

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 22, 2022
 */
class PublicWifiDetector : Detector, Journalist {
    override val name = "Public Wifis"
    override val requiresGpsData = true

    @OptIn(ExperimentalStdlibApi::class)
    override fun getScore(userInput: UserInput, context: BeanFactory): Rule {
        fun fallback(): Rule = Rule.EMPTY_RULE

        val latitude = userInput.gpsLatitude.getOrNull() ?: return fallback()
        val longitude = userInput.gpsLongitude.getOrNull() ?: return fallback()

        val wifimap = context.getBean(WifimapService::class.java)
        val wigle = context.getBean(WigleService::class.java)
        val geoLocation = context.getBean(GeoLocationService::class.java)

        return wifimap.findPublicWifisAround(latitude, longitude, 0.001200, geoLocation.locateIP(userInput.ip).city).let {
                if (it.isEmpty()) {
                    val wigles = wigle.findPublicWifisAround(latitude, longitude, 0.001200)
                    if (wigles.isEmpty())
                        Rule.EMPTY_RULE
                    else Rule(this, "User is most likely connected to a public wifi network! (${wigles[0].ssid}", '+', 50.0)
                } else Rule(this, "User is most likely connected to a public wifi network! (${it[0].ssid}", '+', 50.0)
            }
        }
}