package net.iceyleagons.junction.detectors.impl

import net.iceyleagons.junction.api.geocoding.GeoCodingService
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
class ShippingAnomalyDetector : Detector, Journalist {

    override val name = "Shipping Address Discrepancies"
    override val requiresGpsData = false
    override val maxScore: Int = 100

    @OptIn(ExperimentalStdlibApi::class)
    override fun getScore(userInput: UserInput, context: BeanFactory): Rule {
        val geoCodingService = context.getBean(GeoCodingService::class.java)
        val shipping = geoCodingService.code(userInput.shippingAddress.getOrNull() ?: return Rule.EMPTY_RULE)

        if (shipping.clazz == "google_type") {
            return when (shipping.type) {
                "street_address", "neighborhood" -> {
                    Rule.EMPTY_RULE
                }

                else -> {
                    Rule(this, "Area is likely not a house!", '+', 90.0)
                }
            }
        }

        if (shipping.clazz == "highway") {
            return when (shipping.type) {
                "residential", "living_street" -> {
                    Rule.EMPTY_RULE
                }

                else -> {
                    Rule(this, "Area is likely not a house!", '+', 90.0)
                }
            }
        }

        if (shipping.clazz == "building") {
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