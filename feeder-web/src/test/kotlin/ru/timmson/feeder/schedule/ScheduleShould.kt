package ru.timmson.feeder.schedule

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import ru.timmson.feeder.calendar.ProdCal
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.service.FeederFacade

@ExtendWith(MockitoExtension::class)
class ScheduleShould {

    private lateinit var schedule: Schedule
    private lateinit var feederConfig: FeederConfig

    @Mock
    private lateinit var prodCal: ProdCal

    @Mock
    private lateinit var feederFacade: FeederFacade

    @BeforeEach
    fun setUp() {
        feederConfig = FeederConfig()

        schedule = Schedule(feederConfig, prodCal, feederFacade)
        schedule.init()
    }

    @Test
    fun sendStocksToOwnerOnWorkingDays() {
        `when`(prodCal.isWorking(any())).thenReturn(true)

        schedule.sendStocksToOwner()

        verify(feederFacade).sendStocksToOwner()
    }

    @Test
    fun notSendStocksToOwnerOnHolidays() {
        `when`(prodCal.isWorking(any())).thenReturn(false)

        schedule.sendStocksToOwner()

        verifyNoInteractions(feederFacade)
    }
}