package net.iceyleagons.junction.services

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import net.iceyleagons.junction.api.DetectionService
import net.iceyleagons.junction.detectors.Detector
import net.iceyleagons.junction.detectors.Rule
import net.iceyleagons.junction.detectors.UserInput
import net.iceyleagons.junction.detectors.impl.*
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.BeanFactory
import org.springframework.stereotype.Service

/**
 * @author TOTHTOMI, G4be_
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@Service
class DetectionServiceImpl(val beanFactory: BeanFactory, val json: Json) : DetectionService {

    val logger: Logger = LoggerFactory.getLogger(DetectionServiceImpl::class.java)

    companion object {
        val detectors: MutableList<Detector> = ArrayList()
    }

    init {
        registerDetector(AccountTakeoverDetector())
        registerDetector(LocationIPDifferenceDetector())
        registerDetector(ProxyDetector())
        registerDetector(PublicWifiDetector())
        registerDetector(ShipBillDifferenceDetector())
        registerDetector(ShippingAnomalyDetector())
    }

    override fun checkForFraud(userInput: UserInput): JSONObject {
        val start = System.currentTimeMillis()
        val data = FraudScore(score = .0, calculationTime = 0)

        var providedNumber = 0

        for (detector in detectors) {
            if (detector.requiresGpsData && (userInput.gpsLatitude.isEmpty && userInput.gpsLongitude.isEmpty))
                continue

            data.detectors += detector.name
            val rule = detector.getScore(userInput, beanFactory)
            if (rule != Rule.EMPTY_RULE) {
                when (rule.operation) {
                    '+' -> {
                        data.score += rule.score
                    }
                    '-' -> {
                        data.score -= rule.score
                    }
                }

                if (rule.score > 0) {
                    providedNumber++
                }
                data.data += rule
            }
        }

        // TODO possibly have weighted scores from detectors???

        // Since we can have many detectors which will all give a score from 0-100, we need to get the average
        if (providedNumber > 1)
            data.score /= providedNumber

        logger.info("Calculated fraud score of {} for user input.", data.score)

        data.calculationTime = System.currentTimeMillis() - start
        return JSONObject(json.encodeToString(data))
    }

    @Serializable
    data class FraudScore(
        @SerialName("total_score") var score: Double,
        @SerialName("checked_against") val detectors: MutableList<String> = ArrayList(4),
        @SerialName("calc_ms") var calculationTime: Long,
        @SerialName("applied_rules") val data: MutableList<Rule> = ArrayList()
    )

    final override fun registerDetector(detector: Detector) {
        if (!detectors.contains(detector)) {
            logger.info("Registering detector: {}", detector.name)
            detectors.add(detector)
        }
    }

    override fun getRegisteredDetectors(): List<Detector> = detectors
}