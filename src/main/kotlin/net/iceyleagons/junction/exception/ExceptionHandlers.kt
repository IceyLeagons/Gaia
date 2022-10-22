package net.iceyleagons.junction.exception

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 22, 2022
 */
@ControllerAdvice
class ExceptionHandlers {

    @ExceptionHandler(GeoLocationException::class)
    fun handleException(geoLocationException: GeoLocationException): Map<String, Any?> {
        return mapOf(Pair("error", geoLocationException.message))
    }

    @ExceptionHandler(GeoCodingException::class)
    fun handleException(geoLocationException: GeoCodingException): Map<String, Any?> {
        return mapOf(Pair("error", geoLocationException.message))
    }
}