package ru.timmson.feeder.lingua

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.eq
import ru.timmson.feeder.lingua.model.ExplainResponse
import ru.timmson.feeder.lingua.oxford.OxfordDictionaryClient
import ru.timmson.feeder.lingua.oxford.OxfordDictionaryParser

@ExtendWith(MockitoExtension::class)
class LinguaServiceShould(
) {

    private lateinit var linguaService: LinguaService

    @Mock
    private lateinit var oxfordDictionaryClient: OxfordDictionaryClient

    @Mock
    private lateinit var oxfordDictionaryParser: OxfordDictionaryParser

    @BeforeEach
    fun setUp() {
        linguaService = LinguaService(oxfordDictionaryClient, oxfordDictionaryParser)
    }


    @Test
    fun explain() {
        val word = "word"
        val expected = ExplainResponse(meanings = emptyList())
        val rawResponse = "raw response"

        `when`(oxfordDictionaryClient.fetch(eq(word))).thenReturn(rawResponse)
        `when`(oxfordDictionaryParser.parse(eq(rawResponse))).thenReturn(expected)

        val actual = linguaService.explain(word)

        assertEquals(expected, actual)
    }
}
