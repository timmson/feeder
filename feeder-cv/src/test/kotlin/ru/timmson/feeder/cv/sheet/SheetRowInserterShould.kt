package ru.timmson.feeder.cv.sheet

import com.google.api.services.sheets.v4.model.Color
import com.google.api.services.sheets.v4.model.GridCoordinate
import com.google.api.services.sheets.v4.model.GridRange
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.timmson.feeder.cv.model.Fields

class SheetRowInserterShould() {

    private lateinit var sheetRowInserter: SheetRowInserter

    private val sheetIdNumber = 100
    private val rowIdNumber = 200

    @BeforeEach
    fun setUp() {
        sheetRowInserter = SheetRowInserter(sheetIdNumber, rowIdNumber, Fields("fio", "2", "3", "4", "5", "url"))
    }

    @Test
    fun insertEmptyRow() {
        val request = sheetRowInserter.insertEmptyRow()

        val expected = GridRange().apply {
            sheetId = sheetIdNumber
            startRowIndex = rowIdNumber
            endRowIndex = rowIdNumber + 1
        }

        assertEquals(expected, request.insertRange.range)
        assertEquals("ROWS", request.insertRange.shiftDimension)
    }

    @Test
    fun pasteRow() {
        val request = sheetRowInserter.pasteRow()

        val expected = GridCoordinate().apply {
            sheetId = sheetIdNumber
            rowIndex = rowIdNumber
        }

        assertEquals(expected, request.pasteData.coordinate)
        assertEquals("=HYPERLINK(\"url\"; \"fio\"),2,3,4,5", request.pasteData.data)
    }

    @Test
    fun `fill cell background by yellow`() {
        val expectedColor = Color().apply {
            red = 1.0F
            green = 1.0F
        }
        val expectedCoordinates = GridRange().apply {
            sheetId = sheetIdNumber
            startRowIndex = rowIdNumber
            startColumnIndex = 5
            endRowIndex = rowIdNumber + 1
            endColumnIndex = 6
        }

        val updaterRequest = sheetRowInserter.fillCellBackgroundByYellow(expectedCoordinates.startColumnIndex).updateCells
        val actualColor = updaterRequest.rows[0].getValues()[0].userEnteredFormat.backgroundColor
        val actualCoordinates = updaterRequest.range

        assertEquals(expectedColor, actualColor)
        assertEquals(expectedCoordinates, actualCoordinates)
    }

    @Test
    fun `fill cells background by white`() {
        val expectedColor = Color().apply {
            red = 1.0F
            green = 1.0F
            blue = 1.0F
        }
        val expectedCoordinates = GridRange().apply {
            sheetId = sheetIdNumber
            startRowIndex = rowIdNumber
            startColumnIndex = 0
            endRowIndex = rowIdNumber + 1
            endColumnIndex = 10
        }

        val updaterRequest = sheetRowInserter.fillRowByWhite().updateCells
        val actualColor = updaterRequest.rows[0].getValues()[0].userEnteredFormat.backgroundColor
        val actualCoordinates = updaterRequest.range

        assertEquals(expectedColor, actualColor)
        assertEquals(expectedCoordinates, actualCoordinates)
    }
}
