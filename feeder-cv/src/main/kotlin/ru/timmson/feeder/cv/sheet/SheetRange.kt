package ru.timmson.feeder.cv.sheet

import com.google.api.services.sheets.v4.model.ValueRange

data class SheetRange(
    val row: List<Any>
) {

    val value: ValueRange
        get() = ValueRange().apply {
            majorDimension = "ROWS"
            setValues(listOf(row))
        }
}
