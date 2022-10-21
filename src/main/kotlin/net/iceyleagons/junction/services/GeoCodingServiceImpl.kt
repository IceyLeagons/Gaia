package net.iceyleagons.junction.services

import net.iceyleagons.junction.api.geocoding.GeoCodingResponse
import net.iceyleagons.junction.api.geocoding.ReverseGeoCodingResponse
import net.iceyleagons.junction.api.geocoding.GeoCodingService
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.math.BigDecimal

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@Service
class GeoCodingServiceImpl(restTemplateBuilder: RestTemplateBuilder) : GeoCodingService {

    val restTemplate: RestTemplate = restTemplateBuilder.build()

    override fun decode(lat: BigDecimal, long: BigDecimal): ReverseGeoCodingResponse {
        val json: String = restTemplate.getForObject("https://geocode.maps.co/reverse?lat=${lat}&lon=${long}", String::class)
        return ReverseGeoCodingResponse.fromJson(json)
    }

    override fun code(query: String): GeoCodingResponse {
        val json: String = restTemplate.getForObject("https://geocode.maps.co/search?q=${query}", String::class)
        return GeoCodingResponse.fromJson(json)
    }
}