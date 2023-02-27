package ru.timmson.feeder.web

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import ru.timmson.feeder.service.FeederFacade
import ru.timmson.feeder.stock.model.Stock
import ru.timmson.feeder.stock.service.StockService
import ru.timmson.feeder.web.controller.StockController
import java.math.BigDecimal


@ExtendWith(MockitoExtension::class)
@WebFluxTest(controllers = [StockController::class])
class StockControllerShould {

    @MockBean
    private lateinit var stockService: StockService

    @MockBean
    private lateinit var feederFacade: FeederFacade

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun returnAllStocksOnRequest() {
        val stocks = listOf(Stock("x", BigDecimal.TEN))
        val expected = ObjectMapper().writeValueAsString(stocks)

        `when`(stockService.findAll()).thenReturn(stocks)

        webClient.get().uri("/stock/all").exchange().expectBody().json(expected)
    }

    @Test
    fun sendAllStocksToOwner() {
        webClient.get().uri("/stock/send").exchange().expectStatus().isOk

        verify(feederFacade).sendStocksToChannel()
    }
}