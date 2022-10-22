package net.iceyleagons.junction.detectors.impl

import net.iceyleagons.junction.api.geocoding.GeoCodingService
import net.iceyleagons.junction.api.geolocation.GeoLocationService
import net.iceyleagons.junction.detectors.Detector
import net.iceyleagons.junction.detectors.Rule
import net.iceyleagons.junction.detectors.UserInput
import net.iceyleagons.junction.utils.EarthDistance
import org.springframework.beans.factory.BeanFactory
import kotlin.jvm.optionals.getOrNull

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 22, 2022
 */
class AccountTakeoverDetector : Detector {

    override val name: String = "Account Takeover (Impossible Travel)"
    override val requiresGpsData = false

    private val maxDistanceCar = 1000 * 120 // 120 km (in meters)
    private val maxDistanceBetween = 1000 * 450 // 450 km (this is middle route, when we're uncertain. Since we don't have velocity data, we can only guess)
    private val maxDistanceAirliner = 1000 * 1000 // 1000km (in meters, average distance by an airliner in an hour is 1000 km)

    private val elapsedTimeThreshold = 1000 * 60 * 60 // 1 hour

    @OptIn(ExperimentalStdlibApi::class)
    override fun getScore(userInput: UserInput, context: BeanFactory): Rule {
        val ips = userInput.previousIPs.getOrNull() ?: return Rule.EMPTY_RULE
        if (ips.isEmpty()) return Rule.EMPTY_RULE

        val geoLoc = context.getBean(GeoLocationService::class.java)
        val geoCodingService = context.getBean(GeoCodingService::class.java)

        val recentIpEntry = ips.lastEntry()
        val recentTime = recentIpEntry.key
        val recentIp = geoLoc.locateIP(recentIpEntry.value) // most recent one
        val recentIpDetailed = geoCodingService.decode(recentIp).address

        val currentTime = System.currentTimeMillis()
        val currentIp = geoLoc.locateIP(userInput.ip)
        val currentIpDetailed = geoCodingService.decode(currentIp).address

        val elapsedTime = currentTime - recentTime
        val distanceInMeters = EarthDistance.calculate(currentIp.latitude, currentIp.longitude, recentIp.latitude, recentIp.longitude)

        if (currentIpDetailed.countryCode == recentIpDetailed.countryCode &&
            currentIpDetailed.state == recentIpDetailed.state &&
            currentIpDetailed.county == recentIpDetailed.state) {
            // Same country, same state, same county. We are likely travelling with a car.

            if (maxDistanceCar <= distanceInMeters && elapsedTime <= elapsedTimeThreshold) {
                return if (currentIpDetailed.region == recentIpDetailed.region)
                    Rule(this, "User IP address history indicates impossible travel! (User travelled $distanceInMeters meters in 1 hour in the same region. Vehicle is most definitely a car.)", '+', 70.0)
                else
                    Rule(this, "User IP address history indicates impossible travel! (User travelled $distanceInMeters meters in 1 hour in the same state/county. Vehicle is probably a car.)", '+', 60.0)
            }
        }

        if (currentIpDetailed.countryCode.lowercase() == "us" && recentIpDetailed.countryCode.lowercase() == "us" &&
            currentIpDetailed.state != recentIpDetailed.state) {
            // Very in the US, not the same state. We are very likely to be travelling with a plane

            if (maxDistanceAirliner <= distanceInMeters && elapsedTime <= elapsedTimeThreshold) {
                return Rule(this, "User IP address history indicates impossible travel! (User travelled $distanceInMeters meters in 1 hour in the US, to a different state. Vehicle is most definitely an airliner.)", '+', 70.0)
            }
        }

        if (currentIpDetailed.countryCode != recentIpDetailed.countryCode) {
            // We are not in the same country, we could be either travelling with a plane, or a car.
            // Because of this we will take a middle distance
            if (maxDistanceBetween <= distanceInMeters && elapsedTime <= elapsedTimeThreshold) {
                return Rule(this, "User IP address history indicates impossible travel! (User travelled $distanceInMeters meters in 1 hour to a different country. Vehicle can either be an airliner or a car.)", '+', 60.0)
            }
        }

        return Rule.EMPTY_RULE
    }
}