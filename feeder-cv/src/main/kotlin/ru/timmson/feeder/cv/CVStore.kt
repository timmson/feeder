package ru.timmson.feeder.cv

import org.springframework.stereotype.Service
import ru.timmson.feeder.cv.model.Fields
import ru.timmson.feeder.cv.sheet.SheetService

@Service
class CVStore(
    private val sheetService: SheetService
) {

    fun add(fields: Fields) {
        try {
            sheetService.add(fields)
        } finally {

        }
    }

}
