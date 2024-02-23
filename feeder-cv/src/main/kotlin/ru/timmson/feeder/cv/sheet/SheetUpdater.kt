package ru.timmson.feeder.cv.sheet

import com.google.api.services.sheets.v4.model.*
import ru.timmson.feeder.cv.model.Fields

class SheetUpdater(
    private val sheetIdNumber: Int,
    private val fields: Fields
) {

    val batchUpdateSpreadsheetRequest: BatchUpdateSpreadsheetRequest
        get() = BatchUpdateSpreadsheetRequest().apply {
            requests = listOf(
                insertEmptyRowR3(),
                pasteRowR3(),
                fillYellowCellR3C5()
            )
        }

    private fun insertEmptyRowR3(): Request =
        Request().apply {
            insertRange = InsertRangeRequest().apply {
                range = GridRange().apply {
                    sheetId = sheetIdNumber
                    startRowIndex = 3
                    endRowIndex = 4
                }
                shiftDimension = "ROWS"
            }
        }

    private fun pasteRowR3(): Request =
        Request().apply {
            pasteData = PasteDataRequest().apply {
                data = "=HYPERLINK(\"${fields.url}\"; \"${fields.name}\"),${fields.area},${fields.title},${fields.type},${fields.date}"
                delimiter = ","
                type = "PASTE_NORMAL"
                coordinate = GridCoordinate().apply {
                    sheetId = sheetIdNumber
                    rowIndex = 3
                }
            }
        }

    private fun fillYellowCellR3C5(): Request =
        Request().apply {
            updateCells = UpdateCellsRequest().apply<UpdateCellsRequest> {
                rows = listOf(
                    RowData().apply {
                        setValues(listOf(CellData().apply {
                            userEnteredFormat = CellFormat().apply {
                                backgroundColor = Color().apply {
                                    red = 1.0F
                                    green = 1.0F
                                }
                            }
                        }).toMutableList())
                    }
                )
                setFields("userEnteredFormat.backgroundColor")
                start = GridCoordinate().apply {
                    sheetId = sheetIdNumber
                    rowIndex = 3
                    columnIndex = 5
                }
            }
        }

}
