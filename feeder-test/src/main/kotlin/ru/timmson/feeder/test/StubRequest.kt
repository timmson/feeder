package ru.timmson.feeder.test

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.RecordedRequest

class StubRequest(private val request: RecordedRequest) {

    val mediaType: MediaType
        get() = request.headers["content-type"]!!.toMediaType()

    val path: String
        get() = request.path.toString()

    val method: HttpMethod
        get() = HttpMethod.valueOf(request.method ?: "")

    val body: String
        get() = request.body.readUtf8()

}
