package net.iceyleagons.gaia.detectors.impl

import net.iceyleagons.gaia.api.geolocation.GeoLocationService
import net.iceyleagons.gaia.detectors.Detector
import net.iceyleagons.gaia.detectors.Rule
import net.iceyleagons.gaia.detectors.UserInput
import net.iceyleagons.gaia.utils.Journalist
import org.springframework.beans.factory.BeanFactory

/**
 * This detector handles vpn, proxy and tor detection.
 *
 * @author TOTHTOMI, Gabe
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
class BadIPDetector : Detector, Journalist {

    override val requiresGpsData = false
    override val name = "Bad IP Detector"
    override val maxScore: Int = 90

    override fun getScore(userInput: UserInput, context: BeanFactory): Rule {
        val geoLocationService = context.getBean(GeoLocationService::class.java)
        val result = geoLocationService.locateIP(userInput.ip)

        logger.info("Matching ip ${userInput.ip} against proxied/vpn/tor connections.")
        return with(result) {
            if (tor) Rule(this@BadIPDetector, "IP address likely to be a TOR exit node!", '+', 90.0)
            else if (vpn) Rule(this@BadIPDetector, "IP address is probably a VPN server!", '+', 80.0)
            else if (proxied) Rule(
                this@BadIPDetector,
                "IP address appears to be routed thru a proxy server!",
                '+',
                20.0
            )
            else Rule.EMPTY_RULE
        }
    }
}