package net.iceyleagons.gaia.detectors.impl

import net.iceyleagons.gaia.api.geocoding.GeoCodingService
import net.iceyleagons.gaia.detectors.Detector
import net.iceyleagons.gaia.detectors.Rule
import net.iceyleagons.gaia.detectors.UserInput
import net.iceyleagons.gaia.utils.Journalist
import org.springframework.beans.factory.BeanFactory
import kotlin.jvm.optionals.getOrNull

/**
 * This detector handles differences in the locations of shipping and billing addresses.
 *
 * @author Gabe
 * @version 1.0.0
 * @since Oct. 22, 2022
 */
class ShipBillDifferenceDetector : Detector, Journalist {

    override val name = "Shipping & Billing Address"
    override val requiresGpsData = false
    override val maxScore: Int = 37

    @OptIn(ExperimentalStdlibApi::class)
    override fun getScore(userInput: UserInput, context: BeanFactory): Rule {
        val geoCodingService = context.getBean(GeoCodingService::class.java)
        val billing = geoCodingService.codeToReverse(userInput.billingAddress.getOrNull() ?: return Rule.EMPTY_RULE)
        val shipping = geoCodingService.codeToReverse(userInput.shippingAddress.getOrNull() ?: return Rule.EMPTY_RULE)

        // TODO future (possible improvement): add shipping and billing name
        // TODO future (possible improvement): check phone numbers

        var score = 0.0
        val cause = ArrayList<String>()
        if (billing.address.country != shipping.address.country) {
            score += 20
            cause += "not the same country. (\"${billing.address.country}\" - \"${shipping.address.country}\")"
        }

        if (billing.address.state != shipping.address.state) {
            score += 10
            cause += "not the same state. (\"${
                billing.address.state.let {
                    it.ifEmpty { "NO STATE" }
                }
            }\" - \"${
                shipping.address.state.let {
                    it.ifEmpty { "NO STATE" }
                }
            }\")"
        }

        if (billing.address.region != shipping.address.region || billing.address.county != billing.address.county) {
            score += 5
            cause += "not the same county/region. (\"${billing.address.region}\" - \"${shipping.address.region}\")"
        }

        if (billing.address.city != shipping.address.city) {
            score += 2
            cause += "not the same city. (\"${billing.address.city}\" - \"${shipping.address.city}\")"
        }

        return if (cause.isEmpty())
            Rule.EMPTY_RULE
        else Rule(this, "Detected anomalies:" + cause.joinToString(prefix = "\\n - ", separator = "\\n - "), '+', score)
    }

}