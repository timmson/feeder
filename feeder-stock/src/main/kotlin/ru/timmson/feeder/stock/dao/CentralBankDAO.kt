package ru.timmson.feeder.stock.dao

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.model.Envelope
import ru.timmson.feeder.stock.model.MainInfo
import java.math.BigDecimal
import java.math.RoundingMode

@Service
@CacheConfig(cacheNames = ["mainInfo"])
open class CentralBankDAO(
    private val requester: Requester
) {

    @Cacheable
    open fun getMainInfo(): MainInfo {
        try {
            val request = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:web=\"http://web.cbr.ru/\">\n" +
                    "   <soap:Header/>\n" +
                    "   <soap:Body>\n" +
                    "      <web:MainInfoXML/>\n" +
                    "   </soap:Body>\n" +
                    "</soap:Envelope>"

            val response = requester.postSOAP("https://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx", "http://web.cbr.ru/MainInfoXML", request)

            val reqData = XmlMapper().readValue(response, Envelope::class.java).body?.mainInfoXMLResponse?.mainInfoXMLResult?.regData

            return MainInfo(
                keyRate = BigDecimal(reqData?.keyRate ?: 0.0).setScale(2, RoundingMode.HALF_UP),
                inflation = BigDecimal(reqData?.inflation ?: 0.0).setScale(2, RoundingMode.HALF_UP)
            )
        } catch (e: Exception) {
            throw StockDAOException(e)
        }
    }
}
