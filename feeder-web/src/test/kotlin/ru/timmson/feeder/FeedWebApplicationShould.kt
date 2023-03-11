package ru.timmson.feeder

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class FeedWebApplicationShould {

    @Test
    fun printInfo() {
        assertNotNull(FeedWebApplication().printInfo())
    }

}
