package ru.timmson.feeder.stock.dao

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
import ru.timmson.feeder.common.FileStorage
import ru.timmson.feeder.stock.model.Indicator
import java.math.BigDecimal
import java.math.RoundingMode

@ExtendWith(MockitoExtension::class)
class CommonStorageDAOShould {

    private lateinit var commonStorageDAO: CommonStorageDAO

    @Mock
    private lateinit var fileStorage: FileStorage

    @Mock
    private lateinit var objectMapper: ObjectMapper


    @BeforeEach
    fun setUp() {
        commonStorageDAO = CommonStorageDAO(fileStorage, objectMapper)
    }

    @Test
    fun `read stock price from file successfully`() {
        val expected = Indicator("spx", BigDecimal(4079.90).setScale(2, RoundingMode.HALF_UP))
        val content = "content"

        `when`(fileStorage.read(eq("stocks.json"))).thenReturn(content)
        `when`(objectMapper.readValue(eq(content), any<TypeReference<Map<String, String>>>())).thenReturn(mapOf("spx" to "4079.9"))
        val actual = commonStorageDAO.getStockByTicker("spx")

        assertEquals(expected, actual)
    }

    @Test
    fun `not read stock price when file is not found`() {
        `when`(fileStorage.read(eq("stocks.json"))).thenThrow(FileNotFoundException())

        assertThrows<StockDAOException> { commonStorageDAO.getStockByTicker("spx") }
    }
}

