package ru.timmson.feeder.cv.sheet

import org.springframework.stereotype.Service
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.cv.model.Fields

@Service
class SheetService(
    private val sheetAPI: SheetAPI
) {

    private val log = logger<SheetService>()

    fun add(fields: Fields) {
        log.info("Entering SheetService add($fields) ...")

        try {
            val spreadsheet = sheetAPI.getInfo()
            val names = spreadsheet.sheets.joinToString(",") { it.properties.title }
            val sheet =
                spreadsheet.sheets.firstOrNull { it.properties.title.equals(fields.title, true) }
                    ?: throw Exception("sheet(${fields.title}) is not found. Possible names are [$names]")

            val request = SheetUpdater(sheet.properties.sheetId, fields).batchUpdateSpreadsheetRequest
            val response = sheetAPI.batchUpdate(request)

            log.info("Leaving SheetService add(...) = $response")
        } catch (e: Exception) {
            log.info("Leaving SheetService add(...) = Exception: ${e.message}")
        }
    }

}

