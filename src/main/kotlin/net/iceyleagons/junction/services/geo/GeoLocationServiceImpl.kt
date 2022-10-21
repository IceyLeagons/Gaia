package net.iceyleagons.junction.services.geo

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.iceyleagons.junction.api.geocoding.responses.GeoCodingResponse
import net.iceyleagons.junction.api.geolocation.GeoLocationResponse
import net.iceyleagons.junction.api.geolocation.GeoLocationService
import net.iceyleagons.junction.api.wifi.responses.WifimapHotspot
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@Service
class GeoLocationServiceImpl(restTemplateBuilder: RestTemplateBuilder, @Value("\${apis.seon.key}") apiKey: String, val json: Json,
                             @Value("\${caching.expirationMinutes}") val expire: Int, @Value("\${caching.maxSize}") val maxSize: Long) : GeoLocationService {

    val restTemplate: RestTemplate = restTemplateBuilder.defaultHeader("x-api-key", apiKey).build()

    val cache: Cache<Long, GeoLocationResponse> by lazy {
        Caffeine.newBuilder()
            .maximumSize(maxSize)
            .expireAfterWrite(expire.hours.toJavaDuration())
            .build()
    }

    override fun locateIP(ip: String): GeoLocationResponse =
        cache.get(ip.hashCode().toLong()) {
            val rawJson: String = restTemplate.getForObject("https://api.seon.io/SeonRestService/ip-api/v1.1/${ip}")
            json.decodeFromString((JSONObject(rawJson)["data"] as JSONObject).toString())
        }

    override val accurateEnoughForPublicScanning = false
}