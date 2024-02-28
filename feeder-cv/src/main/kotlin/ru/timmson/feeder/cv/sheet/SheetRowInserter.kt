package ru.timmson.feeder.cv.sheet

import com.google.api.services.sheets.v4.model.*
import ru.timmson.feeder.cv.model.Fields

class SheetRowInserter(
    private val sheetIdNumber: Int,
    private val rowIdNumber: Int,
    private val fields: Fields
) {

    val batchUpdateSpreadsheetRequest: BatchUpdateSpreadsheetRequest
        get() = BatchUpdateSpreadsheetRequest().apply {
            requests = listOf(
                insertEmptyRow(),
                pasteRow(),
                fillRowByWhite(),
                fillCellBackgroundByYellow(5)
            )
        }

    fun insertEmptyRow(): Request =
        Request().apply {
            insertRange = InsertRangeRequest().apply {
                range = GridRange().apply {
                    sheetId = sheetIdNumber
                    startRowIndex = rowIdNumber
                    endRowIndex = rowIdNumber + 1
                }
                shiftDimension = "ROWS"
            }
        }

    fun pasteRow(): Request =
        Request().apply {
            pasteData = PasteDataRequest().apply {
                data = "=HYPERLINK(\"${fields.url}\"; \"${fields.name}\"),${fields.area},${fields.title},${fields.type},${fields.date}"
                delimiter = ","
                type = "PASTE_NORMAL"
                coordinate = GridCoordinate().apply {
                    sheetId = sheetIdNumber
                    rowIndex = rowIdNumber
                }
            }
        }

    fun fillCellBackgroundByYellow(columnIndex: Int): Request {
        return fillCellsBackground(
            GridRange().apply {
                sheetId = sheetIdNumber
                startRowIndex = rowIdNumber
                startColumnIndex = columnIndex
                endRowIndex = rowIdNumber + 1
                endColumnIndex = columnIndex + 1
            },
            Color().apply {
                red = 1.0F
                green = 1.0F
            }
        )
    }

    fun fillRowByWhite() = fillCellsBackground(
        GridRange().apply {
            sheetId = sheetIdNumber
            startRowIndex = rowIdNumber
            startColumnIndex = 0
            endRowIndex = rowIdNumber + 1
            endColumnIndex = 10
        },
        Color().apply {
            red = 1.0F
            green = 1.0F
            blue = 1.0F
        }
    )

    private fun fillCellsBackground(range: GridRange, color: Color): Request {
        return Request().apply {
            updateCells = UpdateCellsRequest().apply {
                rows = listOf(
                    RowData().apply {
                        setValues(listOf(CellData().apply {
                            userEnteredFormat = CellFormat().apply {
                                backgroundColor = color
                            }
                        }).toMutableList())
                    }
                )
                setFields("userEnteredFormat.backgroundColor")
                this.range = range
            }
        }
    }
}
