package net.iceyleagons.junction.services

import com.google.maps.FindPlaceFromTextRequest
import com.google.maps.GeoApiContext
import com.google.maps.PlacesApi
import net.iceyleagons.junction.api.GoogleAPIService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
class GoogleAPIServiceImpl(@Value("\${apis.google.maps.key}") val apiKey: String) : GoogleAPIService {

    @Bean("googleGeo")
    fun googleGeoApiContext(): GeoApiContext {
        return GeoApiContext.Builder().apiKey(apiKey).build()
    }

    fun test() {
        PlacesApi.findPlaceFromText(googleGeoApiContext(), "", FindPlaceFromTextRequest.InputType.TEXT_QUERY)
    }

}