package ru.timmson.feeder.cv

import org.springframework.stereotype.Service

@Service
class CVRegistrar {

    fun parse(request: CVRegisterRequest): CV =
        request.caption.lines().let {
            CV().apply {
                name = request.fileName.split(" ").first()
                area = it[1]
                title = it.last().drop(1).uppercase()
            }
        }

}
