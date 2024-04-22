package ru.timmson.feeder.common

import org.springframework.stereotype.Service
import java.io.File
import java.io.IOException

@Service
class FileStorage {

    @Throws(IOException::class)
    fun write(fileName: String, content: String) {
        File("config/$fileName").writeText(content, Charsets.UTF_8)
    }

    @Throws(IOException::class)
    fun read(fileName: String): String =
        File("config/$fileName").readText(Charsets.UTF_8)

}
