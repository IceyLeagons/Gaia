package net.iceyleagons.junction.services.geo

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.iceyleagons.junction.api.GoogleAPIService
import net.iceyleagons.junction.api.geocoding.GeoCodingService
import net.iceyleagons.junction.api.geocoding.responses.GeoCodingResponse
import net.iceyleagons.junction.api.geocoding.responses.ReverseGeoCodingResponse
import net.iceyleagons.junction.api.geolocation.GeoLocationResponse
import net.iceyleagons.junction.api.wifi.responses.WifimapHotspot
import org.json.JSONArray
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.math.BigDecimal
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@Service
class GeoCodingServiceImpl(restTemplateBuilder: RestTemplateBuilder, val json: Json, val googleAPIService: GoogleAPIService,
                           @Value("\${caching.expirationMinutes}") val expire: Int, @Value("\${caching.maxSize}") val maxSize: Long) : GeoCodingService {

    val restTemplate: RestTemplate = restTemplateBuilder.build()

    val decodeCache: Cache<Long, ReverseGeoCodingResponse> by lazy {
        Caffeine.newBuilder()
            .maximumSize(maxSize)
            .expireAfterWrite(expire.hours.toJavaDuration())
            .build()
    }

    val codeCache: Cache<Long, GeoCodingResponse> by lazy {
        Caffeine.newBuilder()
            .maximumSize(maxSize)
            .expireAfterWrite(expire.hours.toJavaDuration())
            .build()
    }

    override fun decode(lat: Double, long: Double): ReverseGeoCodingResponse =
        decodeCache.get(listOf(lat, long).hashCode().toLong()) {
        val jsonString: String = restTemplate.getForObject("https://geocode.maps.co/reverse?lat=${lat}&lon=${long}", String::class)
        json.decodeFromString(jsonString)
    }

    override fun code(query: String): GeoCodingResponse =
        codeCache.get(query.hashCode().toLong()) {
            val jsonString: String = restTemplate.getForObject<String>("https://geocode.maps.co/search?q=${query}", String::class).also(::println)
            val array = JSONArray(jsonString)

            if (array.isEmpty) {
                // fallback
                return@get googleAPIService.locateAddress(query)
            }

            json.decodeFromString(array.getJSONObject(0).toString())
        }

    override fun decode(location: GeoLocationResponse): ReverseGeoCodingResponse = decode(location.latitude, location.longitude)

    override fun codeToReverse(query: String): ReverseGeoCodingResponse {
        return with(code(query)) {
            decode(latitude, longitude)
        }
    }
}