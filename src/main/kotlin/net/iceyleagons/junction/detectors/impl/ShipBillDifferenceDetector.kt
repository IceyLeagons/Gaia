package net.iceyleagons.junction.detectors.impl

import net.iceyleagons.junction.api.geocoding.GeoCodingService
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
 * @since Oct. 22, 2022
 */
class ShipBillDifferenceDetector : Detector, Journalist {

    override val name = "Shipping & Billing Address"
    override val requiresGpsData = false

    @OptIn(ExperimentalStdlibApi::class)
    override fun getScore(userInput: UserInput, context: BeanFactory): Rule {
        val geoCodingService = context.getBean(GeoCodingService::class.java)
        val billing = geoCodingService.codeToReverse(userInput.billingAddress.getOrNull() ?: return Rule.EMPTY_RULE)
        val shipping = geoCodingService.codeToReverse(userInput.shippingAddress.getOrNull() ?: return Rule.EMPTY_RULE)

        var score = 0.0
        val cause = ArrayList<String>()
        if (billing.address.country != shipping.address.country) {
            score += 50
            cause += "not the same country. (\"${billing.address.country}\" - \"${shipping.address.country}\")"
        }

        if (billing.address.state != shipping.address.state) {
            score += 25
            cause += "not the same state. (\"${billing.address.state.let {
                it.ifEmpty { "NO STATE" }
            }}\" - \"${shipping.address.state.let {
                it.ifEmpty { "NO STATE" }
            }}\")"
        }

        if (billing.address.region != shipping.address.region || billing.address.county != billing.address.county) {
            score += 15
            cause += "not the same county/region. (\"${billing.address.region}\" - \"${shipping.address.region}\")"
        }

        if (billing.address.city != shipping.address.city) {
            score += 10
            cause += "not the same city. (\"${billing.address.city}\" - \"${shipping.address.city}\")"
        }

        return if (cause.isEmpty())
            Rule.EMPTY_RULE
        else Rule(this, "Detected anomalies:" + cause.joinToString(prefix = "\\n - ", separator = "\\n - "), '+', score)
    }

}