package net.iceyleagons.junction.services

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import net.iceyleagons.junction.api.DetectionService
import net.iceyleagons.junction.detectors.Detector
import net.iceyleagons.junction.detectors.UserInput
import net.iceyleagons.junction.detectors.impl.LocationIPDifferenceDetector
import net.iceyleagons.junction.detectors.impl.ProxyDetector
import org.json.JSONArray
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.BeanFactory
import org.springframework.stereotype.Service
import kotlin.math.roundToInt

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@Service
class DetectionServiceImpl(val beanFactory: BeanFactory, val json: Json) : DetectionService {

    val logger: Logger = LoggerFactory.getLogger(DetectionServiceImpl::class.java)
    val detectors: MutableList<Detector> = ArrayList()

    init {
        registerDetector(ProxyDetector())
        registerDetector(LocationIPDifferenceDetector())
    }

    override fun checkForFraud(userInput: UserInput): JSONObject {
        val start = System.currentTimeMillis()
        val data = FraudScore(score = .0, calculationTime = 0)

        for (detector in detectors) {
            if (detector.requiresAccurateData && (userInput.gpsLatitude.isEmpty && userInput.gpsLongitude.isEmpty))
                continue

            data.detectors += detector.name
            val (deltaScore, obj) = detector.getScore(userInput, beanFactory)
            data.score += deltaScore

            if (obj != null)
                data.data += obj.put("detector", detector.name).toMap().mapValues { it.value as String }
        }

        // TODO possibly have weighted scores from detectors???

        // Since we can have many detectors which will all give a score from 0-100, we need to get the average
        if (detectors.size > 1)
            data.score /= detectors.size

        logger.info("Calculated fraud score of {} for user input.", data.score)

        data.calculationTime = System.currentTimeMillis() - start
        return JSONObject(json.encodeToString(data))
    }

    @Serializable
    data class FraudScore(
        var score: Double,
        val detectors: MutableList<String> = ArrayList(4),
        @SerialName("calc_ms") var calculationTime: Long,
        val data: MutableList<Map<String, String>> = ArrayList()
    )

    final override fun registerDetector(detector: Detector) {
        if (!detectors.contains(detector)) {
            logger.info("Registering detector: {}", detector.name)
            detectors.add(detector)
        }
    }

    override fun getRegisteredDetectors(): List<Detector> = this.detectors
}