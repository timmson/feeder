package ru.timmson.feeder.stock.dao

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import ru.timmson.feeder.stock.model.Indicator
import ru.timmson.feeder.stock.model.MEMarketData
import ru.timmson.feeder.stock.model.MEStock
import ru.timmson.feeder.stock.service.StockFileStorageService
import java.math.BigDecimal
import java.math.RoundingMode

@ExtendWith(MockitoExtension::class)
class MoscowExchangeDAOShould {

    private lateinit var moscowExchangeDAO: MoscowExchangeDAO

    @Mock
    private lateinit var requester: Requester

    @Mock
    private lateinit var objectMapper: ObjectMapper

    @Mock
    private lateinit var stockFileStorageService: StockFileStorageService

    private val response: String = "resp"

    @BeforeEach
    fun setUp() {
        moscowExchangeDAO = MoscowExchangeDAO(
            requester = requester,
            objectMapper = objectMapper,
            stockFileStorageService = stockFileStorageService
        )
    }


    @Test
    fun returnMoscowIndexPrice() {
        val expected = Indicator("imoex", BigDecimal(2153.96).setScale(2, RoundingMode.HALF_UP))
        val meStock = getMeStock("LASTVALUE", "2153.96")

        val url = "https://iss.moex.com/iss/engines/stock/markets/index/securities/IMOEX.json"
        `when`(requester.get(url)).thenReturn(response)
        `when`(objectMapper.readValue(eq(response), eq(MEStock::class.java))).thenReturn(meStock)

        val actual = moscowExchangeDAO.getStockByTicker("imoex")

        assertEquals(expected, actual)
    }

    @Test
    fun returnMoscowRealtyIndexPrice() {
        val expected = Indicator("mredc", BigDecimal(254856))
        val meStock = getMeStock("LASTVALUE", "254855.55")

        val url = "https://iss.moex.com/iss/engines/stock/markets/index/securities/MREDC.json"
        `when`(requester.get(url)).thenReturn(response)
        `when`(objectMapper.readValue(eq(response), eq(MEStock::class.java))).thenReturn(meStock)

        val actual = moscowExchangeDAO.getStockByTicker("mredc")

        assertEquals(expected, actual)
    }

    @Test
    fun handleException() {
        assertThrows<StockDAOException> { moscowExchangeDAO.getStockByTicker("spx1") }
    }


    private fun getMeStock(fieldName: String, price: String): MEStock =
        MEStock().apply {
            marketdata = MEMarketData().apply {
                columns = listOf(fieldName)
                data = listOf(listOf(price))
            }
        }
}

