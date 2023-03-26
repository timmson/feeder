package ru.timmson.feeder.lingua.oxford

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.eq
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.lingua.oxford.model.Meaning
import ru.timmson.feeder.lingua.oxford.model.OxfordDictionaryExplainResponse

@ExtendWith(MockitoExtension::class)
class OxfordDictionaryServiceShould {

    private lateinit var oxfordDictionaryService: OxfordDictionaryService

    private lateinit var feederConfig: FeederConfig

    @Mock
    private lateinit var oxfordDictionaryClient: OxfordDictionaryClient

    @Mock
    private lateinit var oxfordDictionaryParser: OxfordDictionaryParser


    @BeforeEach
    fun setUp() {
        feederConfig = FeederConfig()
        feederConfig.oxfordDictionaryUrl = "https://some.site/"
        oxfordDictionaryService = OxfordDictionaryService(feederConfig, oxfordDictionaryClient, oxfordDictionaryParser)
    }

    @Test
    fun explain() {
        val word = "word"
        val url = "${feederConfig.oxfordDictionaryUrl}definition/english/$word"
        val meanings = emptyList<Meaning>()
        val expected = OxfordDictionaryExplainResponse(url, meanings)
        val rawResponse = "raw response"

        `when`(oxfordDictionaryClient.fetch(eq(url))).thenReturn(rawResponse)
        `when`(oxfordDictionaryParser.parse(eq(rawResponse))).thenReturn(meanings)

        val actual = oxfordDictionaryService.explain(word)

        assertEquals(expected, actual)
    }

}
