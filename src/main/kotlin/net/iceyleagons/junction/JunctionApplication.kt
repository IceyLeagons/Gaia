package net.iceyleagons.junction

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource(value = ["classpath:local.properties"])
class JunctionApplication
fun main(args: Array<String>) {
	runApplication<JunctionApplication>(*args)
}
