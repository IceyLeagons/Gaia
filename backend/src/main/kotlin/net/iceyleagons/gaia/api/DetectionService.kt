package net.iceyleagons.gaia.api

import net.iceyleagons.gaia.detectors.Detector
import net.iceyleagons.gaia.detectors.UserInput
import org.json.JSONObject

/**
 * The service responsible for managing all the detectors.
 *
 * @author TOTHTOMI, Gabe
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
interface DetectionService {

    /**
     * This method is responsible for checking whether a user could be involved in fraudulent activities based on the provided [userInput].
     *
     * @param userInput the provided information with which we have to work.
     * @return a jsonobject containing necessary information for web display.
     */
    fun checkForFraud(userInput: UserInput): JSONObject

    /**
     * Register a new detector.
     *
     * @param detector the detector we wish to register.
     */
    fun registerDetector(detector: Detector)

    /**
     * Get all the registered detectors.
     *
     * @return all the registered detectors.
     */
    @Deprecated("Use DetectionServiceImpl#detectors")
    fun getRegisteredDetectors(): List<Detector>

}