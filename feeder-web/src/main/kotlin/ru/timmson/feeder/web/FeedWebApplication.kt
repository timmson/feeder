package ru.timmson.feeder.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class FeedWebApplication

fun main(args: Array<String>) {
    runApplication<FeedWebApplication>(*args)
}
