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
import ru.timmson.feeder.stock.model.MainInfo
import java.math.BigDecimal
import java.math.RoundingMode

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
}
