package ru.timmson.feeder.stock

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.http.HttpClient
import java.time.Duration

@Configuration
open class FeederStockBeanConfig {

    @Bean
    open fun getObjectMapper(): ObjectMapper =
        ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    @Bean
    open fun getHttpClient(@Value("\${feeder.stock.timeout}") timeout: Long): HttpClient =
        HttpClient.newBuilder().connectTimeout(Duration.ofMillis(timeout)).build()

}