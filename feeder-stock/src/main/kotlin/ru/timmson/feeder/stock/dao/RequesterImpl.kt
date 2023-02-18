package ru.timmson.feeder.stock.dao

import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Service
class RequesterImpl : Requester {

    override fun url(url: String): String {
        val builder = HttpRequest.newBuilder(URI(url))
        val request = builder.GET().build()
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body()
    }
}