package ru.timmson.feeder

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import ru.timmson.feeder.common.logger

@EnableScheduling
@SpringBootApplication
open class FeedWebApplication(
    @Value("\${feeder.version}") private val version: String
) {

    private val log = logger<FeedWebApplication>()

    @PostConstruct
    fun printInfo() {
        log.info("${FeedWebApplication::class.simpleName} version $version")
    }

}

fun main(args: Array<String>) {
    runApplication<FeedWebApplication>(*args)
}
