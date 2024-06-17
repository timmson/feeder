package ru.timmson.feeder.stock.dao

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.model.Envelope
import ru.timmson.feeder.stock.model.curs.CursInfo
import ru.timmson.feeder.stock.model.curs.ValuteCursOnDate
import ru.timmson.feeder.stock.model.main.MainInfo
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate

@Service
open class CentralBankDAO(
    private val requester: Requester
) {

    private val xmlMapper = XmlMapper()

    @Cacheable(cacheNames = ["mainInfo"])
    open fun getMainInfo(): MainInfo {
        try {
            val request = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:web=\"http://web.cbr.ru/\">\n" +
                    "   <soap:Header/>\n" +
                    "   <soap:Body>\n" +
                    "      <web:MainInfoXML/>\n" +
                    "   </soap:Body>\n" +
                    "</soap:Envelope>"

            val response = requester.postSOAP("https://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx", "http://web.cbr.ru/MainInfoXML", request)

            val reqData = extractBody(response)?.mainInfoXMLResponse?.mainInfoXMLResult?.regData

            return MainInfo(
                keyRate = BigDecimal(reqData?.keyRate ?: 0.0).setScale(2, RoundingMode.HALF_UP),
                inflation = BigDecimal(reqData?.inflation ?: 0.0).setScale(2, RoundingMode.HALF_UP)
            )
        } catch (e: Exception) {
            throw StockDAOException(e)
        }
    }


    @Cacheable(cacheNames = ["cursInfo"])
    open fun getCursInfo(date: LocalDate): CursInfo {
        val request = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:web=\"http://web.cbr.ru/\">\n" +
                "   <soap:Header/>\n" +
                "   <soap:Body>\n" +
                "      <web:MainInfoXML/>\n" +
                "       <GetCursOnDateXML xmlns=\"http://web.cbr.ru/\">\n" +
                "           <On_date>$date</On_date>\n" +
                "       </GetCursOnDateXML>\n" +
                "   </soap:Body>\n" +
                "</soap:Envelope>"

        val response = requester.postSOAP("https://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx", "http://web.cbr.ru/GetCursOnDateXML", request)

        val valuteCursOnDate = extractBody(response)?.getCursOnDateXMLResponse?.getCursOnDateXMLResult?.valuteData?.valuteCursOnDate?.associateBy { it.chCode?.uppercase() }

        return CursInfo(
            usd = BigDecimal(valuteCursOnDate?.getOrDefault("USD", ValuteCursOnDate().apply { curs = "0.0" })?.curs).setScale(2, RoundingMode.HALF_UP),
            eur = BigDecimal(valuteCursOnDate?.getOrDefault("EUR", ValuteCursOnDate().apply { curs = "0.0" })?.curs).setScale(2, RoundingMode.HALF_UP)
        )
    }

    private fun extractBody(response: String) = xmlMapper.readValue(response, Envelope::class.java).body
}
