package ru.timmson.feeder.common

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class FeederCommonBeanConfig {

    @Bean
    open fun getObjectMapper(): ObjectMapper =
        ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

}
