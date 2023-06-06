package ru.timmson.feeder

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import ru.timmson.feeder.common.logger

@EnableScheduling
@SpringBootApplication
open class FeedApplication(
    private val version: Version
) {

    private val log = logger<FeedApplication>()

    @PostConstruct
    fun printInfo() {
        log.info("${FeedApplication::class.simpleName} version is $version")
    }

}

fun main(args: Array<String>) {
    runApplication<FeedApplication>(*args)
}
