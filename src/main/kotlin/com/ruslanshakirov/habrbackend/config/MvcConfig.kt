package com.ruslanshakirov.habrbackend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
@Configuration
class MvcConfig: WebMvcConfigurer {
    @Value("\${imagePath}")
    private lateinit var uploadPath: String;
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/img/**")
            .addResourceLocations("file://$uploadPath/")
    }
}
