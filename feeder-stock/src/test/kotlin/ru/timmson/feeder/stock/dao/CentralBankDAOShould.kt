package ru.timmson.feeder.stock.dao

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import ru.timmson.feeder.stock.model.curs.CursInfo
import ru.timmson.feeder.stock.model.main.MainInfo
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class CentralBankDAOShould {

    private lateinit var centralBankDAO: CentralBankDAO

    @Mock
    private lateinit var requester: Requester

    @BeforeEach
    fun setUp() {
        centralBankDAO = CentralBankDAO(requester)
    }

    @Test
    fun getMainInfoSuccessfully() {
        val expected = MainInfo(
            keyRate = BigDecimal(16).setScale(2, RoundingMode.HALF_UP),
            inflation = BigDecimal(7.69).setScale(2, RoundingMode.HALF_UP)
        )

        val arrange =
            "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                    "   <soap:Body>\n" +
                    "      <MainInfoXMLResponse xmlns=\"http://web.cbr.ru/\">\n" +
                    "         <MainInfoXMLResult>\n" +
                    "            <RegData xmlns=\"\">\n" +
                    "               <keyRate Title=\"Ключевая ставка\" Date=\"18.12.2023\">16.0</keyRate>\n" +
                    "               <Inflation Title=\"Инфляция\" Date=\"01.02.2024\">7.69</Inflation>\n" +
                    "               <stavka_ref Title=\"Ставка рефинансирования\" Date=\"18.12.2023\">16.0</stavka_ref>\n" +
                    "               <GoldBaks Title=\"Международные резервы\" Date=\"22.03.2024\">590.1</GoldBaks>\n" +
                    "            </RegData>\n" +
                    "         </MainInfoXMLResult>\n" +
                    "      </MainInfoXMLResponse>\n" +
                    "   </soap:Body>\n" +
                    "</soap:Envelope>"

        `when`(requester.postSOAP(eq("https://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx"), eq("http://web.cbr.ru/MainInfoXML"), any())).thenReturn(arrange)

        val actual = centralBankDAO.getMainInfo()

        assertEquals(expected, actual)
    }

    @Test

    fun getMainInfoWithException() {
        `when`(requester.postSOAP(eq("https://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx"), eq("http://web.cbr.ru/MainInfoXML"), any())).thenReturn("")

        assertThrows<StockDAOException> { centralBankDAO.getMainInfo() }
    }

    @Test
    fun getCursInfoSuccessfully() {
        val expected = CursInfo(
            usd = BigDecimal(89.07).setScale(2, RoundingMode.HALF_UP),
            eur = BigDecimal(95.15).setScale(2, RoundingMode.HALF_UP)
        )

        val arrange = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "    <soap:Body>\n" +
                "        <GetCursOnDateXMLResponse xmlns=\"http://web.cbr.ru/\">\n" +
                "            <GetCursOnDateXMLResult>\n" +
                "                <ValuteData OnDate=\"20240615\" xmlns=\"\">\n" +
                "                    <ValuteCursOnDate>\n" +
                "                        <Vname>Доллар США</Vname>\n" +
                "                        <Vnom>1</Vnom>\n" +
                "                        <Vcurs>89.0658</Vcurs>\n" +
                "                        <Vcode>840</Vcode>\n" +
                "                        <VchCode>USD</VchCode>\n" +
                "                        <VunitRate>89.0658</VunitRate>\n" +
                "                    </ValuteCursOnDate>\n" +
                "                    <ValuteCursOnDate>\n" +
                "                        <Vname>Евро</Vname>\n" +
                "                        <Vnom>1</Vnom>\n" +
                "                        <Vcurs>95.1514</Vcurs>\n" +
                "                        <Vcode>978</Vcode>\n" +
                "                        <VchCode>EUR</VchCode>\n" +
                "                        <VunitRate>95.1514</VunitRate>\n" +
                "                    </ValuteCursOnDate>\n" +
                "                </ValuteData>\n" +
                "            </GetCursOnDateXMLResult>\n" +
                "        </GetCursOnDateXMLResponse>\n" +
                "    </soap:Body>\n" +
                "</soap:Envelope>"

        `when`(requester.postSOAP(eq("https://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx"), eq("http://web.cbr.ru/GetCursOnDateXML"), any())).thenReturn(arrange)

        val actual = centralBankDAO.getCursInfo(LocalDate.of(2024, 6, 17))

        assertEquals(expected, actual)
    }
}
