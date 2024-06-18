package ru.timmson.feeder.stock.dao

import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import ru.timmson.feeder.stock.model.Indicator
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class MainInfoDAOShould {

    private lateinit var mainInfoDAO: MainInfoDAO

    @Mock
    private lateinit var centralBankAPI: CentralBankAPI

    @BeforeEach
    fun setUp() {
        mainInfoDAO = MainInfoDAO(centralBankAPI = centralBankAPI)
    }

    @Test
    fun `get stock by ticker`() {
        val expected = Indicator("xxx", BigDecimal.ONE)

        `when`(centralBankAPI.getMainInfo()).thenReturn(mapOf("xxx" to expected))
        val actual = mainInfoDAO.getStockByTicker("xxx")

        assertEquals(expected, actual)
    }
}
