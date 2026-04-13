package ru.timmson.feeder

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import ru.timmson.feeder.common.logger

@EnableCaching
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
