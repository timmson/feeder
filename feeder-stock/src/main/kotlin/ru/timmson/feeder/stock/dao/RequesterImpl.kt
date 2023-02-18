package ru.timmson.feeder.stock.dao

import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.logging.Logger

@Service
class RequesterImpl : Requester {

    private val log = Logger.getLogger(RequesterImpl::class.java.toString())

    override fun fetch(url: String): String {
        log.info("Entering fetch ($url) ... ")
        val builder = HttpRequest.newBuilder(URI(url))
        val request = builder.GET().build()
        val body = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body()
        log.info("Leaving fetch (...${url.substring(url.length - 10)}) = [length=${body.length}] ")
        return body
    }
}