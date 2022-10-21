package net.iceyleagons.junction.services

import net.iceyleagons.junction.api.geolocation.GeoLocationResponse
import net.iceyleagons.junction.api.geolocation.GeoLocationService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@Service
class GeoLocationServiceImpl(restTemplateBuilder: RestTemplateBuilder, @Value("\${apis.seon.key}") apiKey: String) : GeoLocationService {

    val restTemplate: RestTemplate = restTemplateBuilder.defaultHeader("x-api-key", apiKey).build()

    override fun locateIP(ip: String): GeoLocationResponse {
        val rawJson: String = restTemplate.getForObject("https://api.seon.io/SeonRestService/ip-api/v1.1/${ip}")
        return GeoLocationResponse.fromJson(rawJson)
    }

    override fun accurateEnoughForPublicScanning(): Boolean {
        return false
    }
}