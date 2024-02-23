package ru.timmson.feeder.cv

import org.springframework.stereotype.Service
import ru.timmson.feeder.cv.model.Fields
import ru.timmson.feeder.cv.model.Record
import ru.timmson.feeder.cv.sheet.SheetService

@Service
class CVStore(
    private val airtableAPIClient: AirtableAPIClient,
    private val sheetService: SheetService
) {

    fun add(fields: Fields) {
        airtableAPIClient.add(Record(fields))

        try {
            sheetService.add(fields)
        } finally {

        }
    }

}
