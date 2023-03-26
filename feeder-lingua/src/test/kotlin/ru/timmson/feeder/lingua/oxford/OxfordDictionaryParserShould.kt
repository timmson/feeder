package ru.timmson.feeder.lingua.oxford

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OxfordDictionaryParserShould {

    private lateinit var oxfordDictionaryParser: OxfordDictionaryParser

    @BeforeEach
    fun setUp() {
        oxfordDictionaryParser = OxfordDictionaryParser()
    }

    @Test
    fun parse() {
        val expected = "a piece of work involving careful study of a subject over a period of time, done by school or college students"
        val source = OxfordDictionaryParserShould::class.java.classLoader.getResource("oxford-dictionary.html").readText(Charsets.UTF_8)

        val actual = oxfordDictionaryParser.parse(source)

        assertEquals(4, actual.meanings.size)
        assertEquals(expected, actual.meanings[0].value)
    }

}
