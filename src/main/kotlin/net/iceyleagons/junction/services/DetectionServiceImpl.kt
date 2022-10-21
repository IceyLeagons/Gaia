package net.iceyleagons.junction.services

import net.iceyleagons.junction.api.DetectionService
import net.iceyleagons.junction.detectors.Detector
import net.iceyleagons.junction.detectors.UserInput
import org.json.JSONArray
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.BeanFactory
import org.springframework.stereotype.Service

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@Service
class DetectionServiceImpl(val beanFactory: BeanFactory) : DetectionService {

    val logger: Logger = LoggerFactory.getLogger(DetectionServiceImpl::class.java)
    val detectors: MutableList<Detector> = ArrayList()

    override fun checkForFraud(userInput: UserInput): JSONObject {
        val start = System.currentTimeMillis()
        var currentScore = 0

        val data = JSONArray()
        val decs = JSONArray()

        for (detector in detectors) {
            if (detector.requiresAccurateData && (userInput.gpsLat.isEmpty && userInput.gpsLon.isEmpty)) {
                continue
            }

            decs.put(detector.name)
            val decObject = JSONObject()
            currentScore += detector.getScore(userInput, beanFactory, decObject)

            if (!decObject.isEmpty) {
                data.put(decObject)
            }
        }

        // TODO possibly have weighted scores from detectors???

        // Since we can have many detectors which will all give a score from 0-100, we need to get the average
        if (detectors.size > 1)
            currentScore /= detectors.size

        logger.info("Calculated fraud score of {} for user input.", currentScore)

        val json = JSONObject()
        json.put("score", currentScore)
        json.put("detectors", decs)
        json.put("calc_ms", System.currentTimeMillis() - start)
        json.put("data", data)

        return json
    }

    override fun registerDetector(detector: Detector) {
        if (!detectors.contains(detector)) {
            logger.info("Registering detector: {}", detector.name)
            detectors.add(detector)
        }
    }

    override fun getRegisteredDetectors(): List<Detector> = this.detectors
}