package net.iceyleagons.junction.api

import net.iceyleagons.junction.detectors.Detector
import net.iceyleagons.junction.detectors.UserInput
import org.json.JSONObject

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
interface DetectionService {

    fun checkForFraud(userInput: UserInput): JSONObject

    fun registerDetector(detector: Detector)
    fun getRegisteredDetectors(): List<Detector>

}