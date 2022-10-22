package net.iceyleagons.gaia.exception

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * This class is used for handling all the exceptions that this application throws.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 22, 2022
 */
@ControllerAdvice
class ExceptionHandlers {

    /**
     * Handles the [GeoLocationException].
     */
    @ExceptionHandler(GeoLocationException::class)
    fun handleException(geoLocationException: GeoLocationException): Map<String, Any?> {
        return mapOf(Pair("error", geoLocationException.message))
    }

    /**
     * Handles the [GeoCodingException].
     */
    @ExceptionHandler(GeoCodingException::class)
    fun handleException(geoLocationException: GeoCodingException): Map<String, Any?> {
        return mapOf(Pair("error", geoLocationException.message))
    }
}