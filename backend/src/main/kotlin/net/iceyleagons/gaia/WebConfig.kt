package net.iceyleagons.gaia

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 22, 2022
 */
@EnableWebMvc
@Configuration
class WebConfig : WebMvcConfigurer {

    val CLASSPATH_RESOURCE_LOCATIONS: Array<String> = arrayOf(
        "classpath:/META-INF/resources/", "classpath:/templates/",
        "classpath:/static/", "classpath:/public/"
    )

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/"
            )
        }

        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(*CLASSPATH_RESOURCE_LOCATIONS)
        }
    }

}