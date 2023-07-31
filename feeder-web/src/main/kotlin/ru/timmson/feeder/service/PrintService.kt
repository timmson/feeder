package ru.timmson.feeder.service

import org.springframework.stereotype.Service
import ru.timmson.feeder.common.Date
import ru.timmson.feeder.cv.CV
import java.time.LocalDate

@Service
class PrintService {

    fun printCV(cv: CV, date: LocalDate): String =
        cv.let {
            "=SPLIT(\"${it.name},${it.area},${it.title},${it.type},${Date.format(date)}\"; \",\")"
        }
}
