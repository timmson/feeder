package ru.timmson.feeder

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FeedApplicationShould {

    private lateinit var feedApplication: FeedApplication

    private lateinit var version: Version

    @BeforeEach
    fun setUp() {
        version = Version()
        feedApplication = FeedApplication(version)
    }


    @Test
    fun printInfo() {
        assertNotNull(feedApplication.printInfo())
    }

}
