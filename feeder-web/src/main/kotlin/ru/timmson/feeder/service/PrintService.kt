package ru.timmson.feeder.service

import org.springframework.stereotype.Service
import ru.timmson.feeder.cv.CV

@Service
class PrintService {

    fun printCV(cv: CV, date: String): String =
        cv.let {
            "=SPLIT(\"${it.name},${it.area},${it.title},${it.type},$date\"; \",\")"
        }
}
