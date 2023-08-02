package ru.timmson.feeder.cv

import org.springframework.stereotype.Service

@Service
class CVRegistrar {

    private val missedKeywords = listOf("разработчик", "mobile", "net", "бэкенд")

    fun parse(request: CVRegisterRequest): CV =
        request.caption.lines().let {
            CV().apply {
                name = parseFileName(request.fileName)
                area = it[1]
                title = it.last().drop(1).uppercase()
            }
        }


    internal fun parseFileName(fileName: String) =
        fileName.split(" ", "_", ".").first { it.isNotBlank() && !missedKeywords.contains(it.lowercase()) }

}
