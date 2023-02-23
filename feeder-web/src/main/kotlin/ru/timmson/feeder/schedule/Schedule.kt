package ru.timmson.feeder.schedule

import jakarta.annotation.PostConstruct
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.service.FeederFacade

@Service
class Schedule(
    private val feederConfig: FeederConfig,
    private val feederFacade: FeederFacade
) {

    private val log = logger<Schedule>()

    @PostConstruct
    fun init() {
        log.info("Schedule is ${feederConfig.scheduleStock}")
    }

    @Scheduled(cron = "#{@feederConfig.scheduleStock}")
    fun sendStocksToOwner() = feederFacade.sendStocksToOwner()

}