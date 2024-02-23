package ru.timmson.feeder.cv.sheet

import com.google.api.services.sheets.v4.model.Sheet
import com.google.api.services.sheets.v4.model.SheetProperties
import com.google.api.services.sheets.v4.model.Spreadsheet
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import ru.timmson.feeder.cv.model.Fields

@ExtendWith(MockitoExtension::class)
class SheetServiceShould {

    private lateinit var sheetService: SheetService

    @Mock
    private lateinit var sheetAPI: SheetAPI

    @BeforeEach
    fun setUp() {
        sheetService = SheetService(sheetAPI)
    }

    @Test
    fun `add row when type is known`() {
        val type = "ONE"

        val arrange = Spreadsheet().apply {
            sheets = listOf(
                Sheet().apply {
                    properties = SheetProperties().apply {
                        sheetId = 1
                        title = type.lowercase()
                    }
                },
            )
        }

        `when`(sheetAPI.getInfo()).thenReturn(arrange)
        sheetService.add(Fields("", "", type, "", "", ""))

        verify(sheetAPI, times(1)).batchUpdate(any())
    }

    @Test
    fun `not add row when type is not known`() {
        val arrange = Spreadsheet().apply {
            sheets = emptyList()
        }

        `when`(sheetAPI.getInfo()).thenReturn(arrange)
        sheetService.add(Fields("", "", "", "", "", ""))

        verify(sheetAPI, times(0)).batchUpdate(any())
    }


}
