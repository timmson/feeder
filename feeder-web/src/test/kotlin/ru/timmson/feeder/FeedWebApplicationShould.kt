package ru.timmson.feeder

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FeedWebApplicationShould {

    private lateinit var feedWebApplication: FeedWebApplication

    private lateinit var version: Version

    @BeforeEach
    fun setUp() {
        version = Version()
        feedWebApplication = FeedWebApplication(version)
    }


    @Test
    fun printInfo() {
        assertNotNull(feedWebApplication.printInfo())
    }

}
