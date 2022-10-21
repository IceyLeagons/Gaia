package net.iceyleagons.junction.services.wifi

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.iceyleagons.junction.api.wifi.WigleService
import net.iceyleagons.junction.api.wifi.responses.WifimapHotspot
import net.iceyleagons.junction.api.wifi.responses.WigleResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
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
class WigleServiceImpl(val restTemplateBuilder: RestTemplateBuilder, @Value("\${apis.wigle.key}") wigleKey: String, val json: Json,
                       @Value("\${caching.expirationMinutes}") val expire: Int, @Value("\${caching.maxSize}") val maxSize: Long): WigleService {

    val wigleTemplate = restTemplateBuilder.defaultHeader("Authorization", "Basic $wigleKey").build()!!

    val cache: Cache<Long, Array<WigleResponse.WigleNetwork>> by lazy {
        Caffeine.newBuilder()
            .maximumSize(maxSize)
            .expireAfterWrite(expire.hours.toJavaDuration())
            .build()
    }

    override fun findPublicWifisAround(
        latitude: Double,
        longitude: Double,
        range: Double
    ): Array<WigleResponse.WigleNetwork> {
        val key = listOf(latitude, longitude, range).hashCode()

        return cache.get(key.toLong()) {
            fun getWigleWifis(lat1: Double, lat2: Double, long1: Double, long2: Double): WigleResponse =
                json.decodeFromString(wigleTemplate.getForObject("https://api.wigle.net/api/v2/network/search?onlymine=false&latrange1=$lat1&latrange2=$lat2&longrange1=$long1&longrange2=$long2&lastupdt=20210101&freenet=true&paynet=false"))

            val lat1 = latitude - range
            val lat2 = latitude + range

            val long1 = longitude - range
            val long2 = longitude + range

            getWigleWifis(lat1, lat2, long1, long2).results
        }
    }

}