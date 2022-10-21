package net.iceyleagons.junction

import net.iceyleagons.junction.api.DetectionService
import net.iceyleagons.junction.detectors.UserInput
import org.json.JSONObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@RestController
class ApiEndpoint(val detectionService: DetectionService) {

    @GetMapping("/runCheck")
    fun test(@RequestBody userInput: UserInput): Map<String, Any> {
        return detectionService.checkForFraud(userInput).toMap()
    }
}