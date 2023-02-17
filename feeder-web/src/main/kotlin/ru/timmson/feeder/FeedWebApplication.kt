package ru.timmson.feeder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class FeedWebApplication

fun main(args: Array<String>) {
    runApplication<FeedWebApplication>(*args)
}
