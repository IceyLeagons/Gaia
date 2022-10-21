package net.iceyleagons.junction

import net.iceyleagons.junction.api.DetectionService
import net.iceyleagons.junction.api.wifi.WifimapService
import net.iceyleagons.junction.detectors.UserInput
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@RestController
class ApiEndpoint(val detectionService: DetectionService, val wifimapService: WifimapService) {

    @GetMapping("/runCheck")
    fun runCheck(@RequestBody userInput: UserInput): Map<String, Any> {
        return detectionService.checkForFraud(userInput).toMap()
    }

    @GetMapping("/test")
    fun test() {
         wifimapService.findPublicWifisAround(46.254067, 20.139238, 0.001200, "Szeged").forEach {
             println(it.ssid)
         } // or 0.001000
    }
}