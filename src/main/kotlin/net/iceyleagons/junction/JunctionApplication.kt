package net.iceyleagons.junction

import com.google.maps.GeoApiContext
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource(value = ["classpath:local.properties"])
class JunctionApplication(@Value("\${apis.google.maps.key}") val apiKey: String) {
	@Bean
	fun json(): Json = Json {
		prettyPrint = false
		coerceInputValues = true
		ignoreUnknownKeys = true
	}

	@Bean("googleGeo")
	fun googleGeoApiContext(): GeoApiContext {
		return GeoApiContext.Builder().apiKey(apiKey).build()
	}
}
fun main(args: Array<String>) {
	runApplication<JunctionApplication>(*args)
}
