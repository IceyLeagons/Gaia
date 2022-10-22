package net.iceyleagons.junction.services.wifi

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import net.iceyleagons.junction.api.wifi.WifimapService
import net.iceyleagons.junction.api.wifi.responses.WifimapHotspot
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.postForObject
import kotlin.time.Duration.Companion.hours
import kotlin.time.toJavaDuration

@Service
class WifimapService(
    val restTemplateBuilder: RestTemplateBuilder, val json: Json,
    @Value("\${caching.expirationMinutes}") val expire: Int, @Value("\${caching.maxSize}") val maxSize: Long
) :
    WifimapService {

    val cache: Cache<Long, Array<WifimapHotspot>> by lazy {
        Caffeine.newBuilder()
            .maximumSize(maxSize)
            .expireAfterWrite(expire.hours.toJavaDuration())
            .build()
    }

    override fun findPublicWifisAround(
        latitude: Double,
        longitude: Double,
        range: Double,
        cityName: String
    ): Array<WifimapHotspot> {
        val key = listOf(latitude, longitude, range).hashCode() + cityName.hashCode()

        return cache.get(key.toLong()) {
            val lat1 = latitude - range
            val lat2 = latitude + range

            val long1 = longitude - range
            val long2 = longitude + range

            val response: String =
                restTemplateBuilder.build().postForObject(
                    "https://api.wifimap.io/graphql_public_api",
                    Query("query fetchHotspotsList { cities(query: \"${cityName}\") { hotspots { address uuid lat lng name ssid category } } }")
                )

            json.decodeFromString<Array<WifimapHotspot>>(
                JSONObject(response).getJSONObject("data").getJSONArray("cities").getJSONObject(0)
                    .getJSONArray("hotspots").toString()
            ).filter {
                (long1 < it.lng && it.lng < long2) && (lat1 < it.lat && it.lat < lat2)
            }.toTypedArray()
        }
    }
}

data class Query(val query: String)