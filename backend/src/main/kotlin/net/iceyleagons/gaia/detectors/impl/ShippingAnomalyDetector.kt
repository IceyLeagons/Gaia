package net.iceyleagons.gaia.detectors.impl

import net.iceyleagons.gaia.api.geocoding.GeoCodingService
import net.iceyleagons.gaia.detectors.Detector
import net.iceyleagons.gaia.detectors.Rule
import net.iceyleagons.gaia.detectors.UserInput
import net.iceyleagons.gaia.utils.Journalist
import org.springframework.beans.factory.BeanFactory
import kotlin.jvm.optionals.getOrNull

/**
 * This detector handles incorrect shipping addresses.
 *
 * @author TOTHTOMI, Gabe
 * @version 1.0.0
 * @since Oct. 22, 2022
 */
class ShippingAnomalyDetector : Detector, Journalist {

    override val name = "Shipping Address Discrepancies"
    override val requiresGpsData = false
    override val maxScore: Int = 90

    @OptIn(ExperimentalStdlibApi::class)
    override fun getScore(userInput: UserInput, context: BeanFactory): Rule {
        val geoCodingService = context.getBean(GeoCodingService::class.java)
        val shipping = geoCodingService.code(userInput.shippingAddress.getOrNull() ?: return Rule.EMPTY_RULE)

        if (shipping.`class` == "google_type") {
            return when (shipping.type) {
                "street_address", "neighborhood" -> {
                    Rule.EMPTY_RULE
                }

                else -> {
                    Rule(this, "Area is likely not a house!", '+', 90.0)
                }
            }
        }

        if (shipping.`class` == "highway") {
            return when (shipping.type) {
                "residential", "living_street" -> {
                    Rule.EMPTY_RULE
                }

                else -> {
                    Rule(this, "Area is likely not a house!", '+', 90.0)
                }
            }
        }

        if (shipping.`class` == "building") {
            return when (shipping.type) {
                "residential", "house" -> {
                    Rule.EMPTY_RULE
                }

                else -> {
                    Rule(this, "Area is likely not a house!", '+', 90.0)
                }
            }
        }

        return Rule.EMPTY_RULE
    }
}