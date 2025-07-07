package ru.timmson.feeder.yandex

import org.apache.pdfbox.Loader
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.springframework.stereotype.Service


@Service
class PDF2String {

    fun convert(file: ByteArray): String {
        var document: PDDocument? = null
        try {
            document = Loader.loadPDF(file)
            return PDFTextStripper().getText(document)
        } finally {
            document?.close()
        }
    }
}