package net.iceyleagons.junction

import kotlinx.serialization.json.Json
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource(value = ["classpath:local.properties"])
class JunctionApplication {
	@Bean
	fun json(): Json = Json {
		prettyPrint = false
		coerceInputValues = true
		ignoreUnknownKeys = true
	}
}
fun main(args: Array<String>) {
	runApplication<JunctionApplication>(*args)
}
