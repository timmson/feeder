package ru.timmson.feeder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
open class FeedWebApplication

fun main(args: Array<String>) {
    runApplication<FeedWebApplication>(*args)
}
