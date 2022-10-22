package net.iceyleagons.junction.detectors.impl

import net.iceyleagons.junction.api.geocoding.GeoCodingService
import net.iceyleagons.junction.api.geolocation.GeoLocationService
import net.iceyleagons.junction.detectors.Detector
import net.iceyleagons.junction.detectors.Rule
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
    override val requiresGpsData = false

    @OptIn(ExperimentalStdlibApi::class)
    override fun getScore(userInput: UserInput, context: BeanFactory): Rule {
        val geoLocationService = context.getBean(GeoLocationService::class.java)
        val geoCodingService = context.getBean(GeoCodingService::class.java)

        // We are focusing on shipping, as billing is more likely to be the victims information.
        val address = geoCodingService.codeToReverse(userInput.shippingAddress.getOrNull() ?: kotlin.run {
            userInput.billingAddress.getOrNull() ?: return@getScore Rule.EMPTY_RULE
        }).address

        val geoLoc = geoLocationService.locateIP(userInput.ip)
        val detailedLocation = geoCodingService.decode(geoLoc).address

        if (address.city == detailedLocation.city && address.country == detailedLocation.country && address.county == detailedLocation.county)
            return Rule.EMPTY_RULE

        if (address.country == detailedLocation.country)
            return Rule(this, "Different city/county detected against IP location (from shipping/billing address)", '+', 10.0)

        return Rule(this, "Different country detected against IP location (from shipping/billing address)", '+', 30.0)
    }
}