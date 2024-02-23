package ru.timmson.feeder.cv.sheet

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.common.logger
import ru.timmson.feeder.cv.model.Fields
import java.io.FileInputStream

@Service
class SheetService(
    private val feederConfig: FeederConfig
) : InitializingBean {

    private val log = logger<SheetService>()

    //private lateinit var credential: GoogleCredential
    private lateinit var sheets: Sheets

    override fun afterPropertiesSet() {
        try {
            val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
            val jsonFactory = GsonFactory.getDefaultInstance()
            val credential = GoogleCredential.fromStream(FileInputStream(feederConfig.spreadSheetSecretFile))
                .createScoped(listOf(SheetsScopes.SPREADSHEETS))
            sheets = Sheets.Builder(httpTransport, jsonFactory, credential).setApplicationName(feederConfig.applicationName).build()
        } catch (t: Throwable) {
            log.severe("Error while initializing Google API - ${t.message}")
        }
    }

    fun add(fields: Fields) {
        log.info("Entering SheetService add($fields) ...")

        val valueRange = SheetRange(
            listOf(
                fields.name,
                fields.area,
                fields.title,
                fields.type,
                fields.date,
                fields.url
            )
        ).value

        val response = sheets.spreadsheets().values()
            .append(feederConfig.spreadSheetId, fields.title, valueRange)
            .setValueInputOption("RAW")
            .execute()

        log.info("Leaving SheetService add(...) = $response")
    }

}

