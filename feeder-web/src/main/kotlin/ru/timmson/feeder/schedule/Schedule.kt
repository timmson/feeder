package ru.timmson.feeder.schedule

import jakarta.annotation.PostConstruct
import org.springframework.cache.CacheManager
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.timmson.feeder.calendar.ProdCal
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.service.FeederFacade
import java.time.LocalDate

@Service
open class Schedule(
    private val feederConfig: FeederConfig,
    private val prodCal: ProdCal,
    private val feederFacade: FeederFacade,
    private val cacheManager: CacheManager
) {

    private val log = logger<Schedule>()

    @PostConstruct
    fun init() {
        log.info("Schedule is ${feederConfig.scheduleStock} and ${feederConfig.scheduleCacheTTL}")
    }

    @Scheduled(cron = "#{@feederConfig.scheduleStock}")
    fun sendStocksToChannel() {
        val isWorking = prodCal.isWorking(LocalDate.now())

        log.info("Entering sendStocksToChannel($isWorking)...")

        if (isWorking) {
            feederFacade.sendStocksToChannel()
        }
    }

    //@CacheEvict("tickers")
    @Scheduled(fixedRateString = "#{@feederConfig.scheduleCacheTTL}")
    open fun clearCache() {
        cacheManager.cacheNames.forEach { cacheManager.getCache(it)?.clear() }
        log.info("Clear cached tickers")
    }

}
