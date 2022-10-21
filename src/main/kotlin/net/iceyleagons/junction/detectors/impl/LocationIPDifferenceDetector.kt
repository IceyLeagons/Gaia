package net.iceyleagons.junction.detectors.impl

import net.iceyleagons.junction.api.geocoding.GeoCodingService
import net.iceyleagons.junction.api.geolocation.GeoLocationService
import net.iceyleagons.junction.detectors.Detector
import net.iceyleagons.junction.detectors.UserInput
import net.iceyleagons.junction.utils.Journalist
import org.json.JSONObject
import org.springframework.beans.factory.BeanFactory
import kotlin.jvm.optionals.getOrNull

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
class LocationIPDifferenceDetector : Detector, Journalist {
    override val name: String = "Address Difference"
    override val requiresAccurateData = false

    @OptIn(ExperimentalStdlibApi::class)
    override fun getScore(userInput: UserInput, context: BeanFactory): Pair<Int, JSONObject?> {
        val geoLocationService = context.getBean(GeoLocationService::class.java)
        val geoCodingService = context.getBean(GeoCodingService::class.java)

        val address = geoCodingService.codeToReverse(userInput.shippingAddress.getOrNull() ?: kotlin.run {
            userInput.billingAddress.getOrNull() ?: return@getScore 0 to null
        }).address

        val geoLoc = geoLocationService.locateIP(userInput.ip)
        val detailedLocation = geoCodingService.decode(geoLoc).address

        if (address.city == detailedLocation.city && address.country == detailedLocation.country && address.county == detailedLocation.county)
            return 0 to null

        if (address.country == detailedLocation.country)
            return 10 to JSONObject().put("anomaly", "Different city/county detected against IP location (from shipping/billing address)")

        return 30 to JSONObject().put("anomaly", "Diferrent country detected against IP location (from shipping/billing address)")
    }
}