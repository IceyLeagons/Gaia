package net.iceyleagons.junction.detectors.impl

import net.iceyleagons.junction.api.geolocation.GeoLocationService
import net.iceyleagons.junction.detectors.Detector
import net.iceyleagons.junction.detectors.Rule
import net.iceyleagons.junction.detectors.UserInput
import net.iceyleagons.junction.utils.Journalist
import org.springframework.beans.factory.BeanFactory

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
class ProxyDetector : Detector, Journalist {

    override val requiresGpsData = false
    override val name = "VPN Detector"
    override val maxScore: Int = 90

    override fun getScore(userInput: UserInput, context: BeanFactory): Rule {
        val geoLocationService = context.getBean(GeoLocationService::class.java)
        val result = geoLocationService.locateIP(userInput.ip)

        logger.info("Matching ip ${userInput.ip} against proxied/vpn/tor connections.")
        return with(result) {
            if (tor) Rule(this@ProxyDetector, "IP address likely to be a TOR exit node!", '+', 90.0)
            else if (vpn) Rule(this@ProxyDetector, "IP address is probably a VPN server!", '+', 80.0)
            else if (proxied) Rule(
                this@ProxyDetector,
                "IP address appears to be routed thru a proxy server!",
                '+',
                20.0
            )
            else Rule.EMPTY_RULE
        }
    }
}