package net.iceyleagons.junction.detectors.impl

import net.iceyleagons.junction.api.geolocation.GeoLocationService
import net.iceyleagons.junction.detectors.Detector
import net.iceyleagons.junction.detectors.UserInput
import net.iceyleagons.junction.utils.Journalist
import org.json.JSONObject
import org.springframework.beans.factory.BeanFactory

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
class ProxyDetector : Detector, Journalist {

    override val requiresAccurateData = false
    override val name = "VPN Detector"

    override fun getScore(userInput: UserInput, context: BeanFactory): Pair<Int, JSONObject?> {
        val geoLocationService = context.getBean(GeoLocationService::class.java)
        val result = geoLocationService.locateIP(userInput.ip)

        logger.info("Matching ip ${userInput.ip} against proxied/vpn/tor connections.")
        return with(result) {
            if (tor) 30 to JSONObject().put("anomaly", "IP address likely to be a TOR exit node!")// to "User was caught using tor"
            else if (vpn) 10 to JSONObject().put("anomaly", "IP address likely to be a VPN server!") // to "User was caught with a vpn"
            else if (proxied) 8 to JSONObject().put("anomaly", "IP address likely to be a proxy server!") //to "User was caught with a possible proxied connection"
            else 0 to null// to "Test"
        }
    }
}