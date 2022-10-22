package net.iceyleagons.gaia

import net.iceyleagons.gaia.api.DetectionService
import net.iceyleagons.gaia.detectors.UserInput
import org.springframework.web.bind.annotation.*

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@RestController
class ApiEndpoint(val detectionService: DetectionService) {

    @PostMapping("/runCheck")
    @CrossOrigin
    fun runCheck(@RequestBody userInput: UserInput): Map<String, Any> {
        return detectionService.checkForFraud(userInput).toMap()
    }
}