package ru.timmson.feeder.test

import okhttp3.mockwebserver.MockResponse

class StubResponse {

    var mockResponse: MockResponse = MockResponse()

    var contentType: String = ""
        set(contentType) {
            mockResponse = mockResponse.addHeader("content-type", contentType)
        }

    var code: Int = 200
        set(code) {
            mockResponse = mockResponse.setResponseCode(code)
        }

    var body: String = ""
        set(body) {
            mockResponse = mockResponse.setBody(body)
        }
}
