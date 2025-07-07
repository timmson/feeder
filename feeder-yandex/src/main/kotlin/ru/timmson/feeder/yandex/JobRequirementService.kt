package ru.timmson.feeder.yandex

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.FileStorage
import ru.timmson.feeder.yandex.model.YandexJob

@Service
class JobRequirementService(
    private val fileStorage: FileStorage,
    private val objectMapper: ObjectMapper
) {

    private val file = "jobs.json"

    fun read(): Map<String, YandexJob> =
        objectMapper.readValue(
            fileStorage.read(file),
            object : TypeReference<Map<String, YandexJob>>() {}
        )

}