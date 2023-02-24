package ru.timmson.feeder.schedule

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.service.FeederFacade

@ExtendWith(MockitoExtension::class)
class ScheduleShould {

    private lateinit var schedule: Schedule
    private val feederConfig = FeederConfig()

    @Mock
    private lateinit var feederFacade: FeederFacade

    @BeforeEach
    fun setUp() {
        schedule = Schedule(feederConfig, feederFacade)
    }

    @Test
    fun sendStocksToOwner() {
        schedule.init()
        schedule.sendStocksToOwner()

        verify(feederFacade).sendStocksToOwner()
    }
}