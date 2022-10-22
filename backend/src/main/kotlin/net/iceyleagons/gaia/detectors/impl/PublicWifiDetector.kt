package net.iceyleagons.gaia.detectors.impl

import net.iceyleagons.gaia.api.geolocation.GeoLocationService
import net.iceyleagons.gaia.api.wifi.WifimapService
import net.iceyleagons.gaia.api.wifi.WigleService
import net.iceyleagons.gaia.detectors.Detector
import net.iceyleagons.gaia.detectors.Rule
import net.iceyleagons.gaia.detectors.UserInput
import net.iceyleagons.gaia.utils.Journalist
import org.springframework.beans.factory.BeanFactory
import kotlin.jvm.optionals.getOrNull

/**
 * This detector handles public wifi-related detection mechanisms.
 *
 * @author Gabe
 * @version 1.0.0
 * @since Oct. 22, 2022
 */
class PublicWifiDetector : Detector, Journalist {

    override val name = "Public Wifis"
    override val requiresGpsData = true
    override val maxScore: Int = 20

    @OptIn(ExperimentalStdlibApi::class)
    override fun getScore(userInput: UserInput, context: BeanFactory): Rule {
        fun fallback(): Rule = Rule.EMPTY_RULE

        val latitude = userInput.gpsLatitude.getOrNull() ?: return fallback()
        val longitude = userInput.gpsLongitude.getOrNull() ?: return fallback()

        val wifimap = context.getBean(WifimapService::class.java)
        val wigle = context.getBean(WigleService::class.java)
        val geoLocation = context.getBean(GeoLocationService::class.java)

        return wifimap.findPublicWifisAround(latitude, longitude, 0.001200, geoLocation.locateIP(userInput.ip).city)
            .let {
                if (it.isEmpty()) {
                    val wigles = wigle.findPublicWifisAround(latitude, longitude, 0.001200)
                    if (wigles.isEmpty())
                        Rule.EMPTY_RULE
                    else Rule(
                        this,
                        "User is most likely connected to a public wifi network! (${wigles[0].ssid})",
                        '+',
                        20.0
                    )
                } else Rule(this, "User is most likely connected to a public wifi network! (${it[0].ssid})", '+', 20.0)
            }
    }
}