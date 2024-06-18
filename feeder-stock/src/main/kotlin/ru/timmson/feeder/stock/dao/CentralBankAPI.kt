package ru.timmson.feeder.stock.dao

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ru.timmson.feeder.stock.model.Envelope
import ru.timmson.feeder.stock.model.Indicator
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate

@Service
open class CentralBankAPI(
    private val requester: Requester
) {

    private val xmlMapper = XmlMapper()

    @Cacheable(cacheNames = ["mainInfo"])
    open fun getMainInfo(): Map<String, Indicator> {
        try {
            val request = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:web=\"http://web.cbr.ru/\">\n" +
                    "   <soap:Header/>\n" +
                    "   <soap:Body>\n" +
                    "      <web:MainInfoXML/>\n" +
                    "   </soap:Body>\n" +
                    "</soap:Envelope>"

            val response = requester.postSOAP("https://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx", "http://web.cbr.ru/MainInfoXML", request)

            val reqData = extractBody(response)?.mainInfoXMLResponse?.mainInfoXMLResult?.regData

            return listOf(
                Indicator(
                    name = "keyRate",
                    price = toBigDecimal(reqData?.keyRate)
                ),
                Indicator(
                    name = "inflation",
                    price = toBigDecimal(reqData?.inflation)
                )
            ).associateBy { it.name }
        } catch (e: Exception) {
            throw StockDAOException(e)
        }
    }


    @Cacheable(cacheNames = ["cursInfo"])
    open fun getCursInfo(date: LocalDate): Map<String, Indicator> {
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


        return listOf(
            Indicator(
                name = "usd",
                price = toBigDecimal(valuteCursOnDate?.get("USD")?.curs)
            ),
            Indicator(
                name = "eur",
                price = toBigDecimal(valuteCursOnDate?.get("EUR")?.curs)
            )
        ).associateBy { it.name }
    }

    private fun extractBody(response: String) = xmlMapper.readValue(response, Envelope::class.java).body

    private fun toBigDecimal(keyRate: Double?): BigDecimal = BigDecimal(keyRate ?: 0.0).setScale(2, RoundingMode.HALF_UP)
}
