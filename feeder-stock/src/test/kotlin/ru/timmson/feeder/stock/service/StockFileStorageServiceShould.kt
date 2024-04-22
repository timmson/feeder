package ru.timmson.feeder.stock.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import okio.FileNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import ru.timmson.feeder.common.FileStorage
import ru.timmson.feeder.stock.dao.StockDAOException
import ru.timmson.feeder.stock.model.Indicator
import java.math.BigDecimal
import java.math.RoundingMode

@ExtendWith(MockitoExtension::class)
class StockFileStorageServiceShould {

    private lateinit var stockFileStorageService: StockFileStorageService

    @Mock
    private lateinit var fileStorage: FileStorage

    @Mock
    private lateinit var objectMapper: ObjectMapper


    @BeforeEach
    fun setUp() {
        stockFileStorageService = StockFileStorageService(fileStorage, objectMapper)
    }

    @Test
    fun `read stock price from file successfully`() {
        val expected = Indicator("spx", BigDecimal(4079.90).setScale(2, RoundingMode.HALF_UP))
        val content = "content"

        `when`(fileStorage.read(eq("stocks.json"))).thenReturn(content)
        `when`(objectMapper.readValue(eq(content), any<TypeReference<Map<String, String>>>())).thenReturn(mapOf("spx" to "4079.9"))
        val actual = stockFileStorageService.getStockByTicker("spx")

        assertEquals(expected, actual)
    }

    @Test
    fun `not read stock price when file is not found`() {
        `when`(fileStorage.read(eq("stocks.json"))).thenThrow(FileNotFoundException())

        assertThrows<StockDAOException> { stockFileStorageService.getStockByTicker("spx") }
    }

    @Test
    fun `write stock price to file successfully`() {
        val expected = "{\"spx\": \"4079.90\"}"
        val indicator = Indicator("spx", BigDecimal(4079.90).setScale(2, RoundingMode.HALF_UP))
        val content = "{}"

        `when`(fileStorage.read(eq("stocks.json"))).thenReturn(content)
        `when`(objectMapper.readValue(eq(content), any<TypeReference<Map<String, String>>>())).thenReturn(emptyMap())
        `when`(objectMapper.writeValueAsString(any())).thenReturn(expected)

        stockFileStorageService.setStock(indicator)

        verify(fileStorage).write(eq("stocks.json"), eq(expected))
    }

    @Test
    fun `not write stock price when file is not found`() {
        val indicator = Indicator("spx", BigDecimal(4079.90).setScale(2, RoundingMode.HALF_UP))

        `when`(fileStorage.read(eq("stocks.json"))).thenThrow(FileNotFoundException())

        assertThrows<StockDAOException> { stockFileStorageService.setStock(indicator) }
    }
}

