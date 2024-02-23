package ru.timmson.feeder.cv.sheet

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse
import com.google.api.services.sheets.v4.model.Spreadsheet
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import ru.timmson.feeder.common.FeederConfig
import ru.timmson.feeder.common.logger
import java.io.FileInputStream

@Service
class SheetAPI(
    private val feederConfig: FeederConfig
) : InitializingBean {

    private val log = logger<SheetAPI>()

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

    fun getInfo(): Spreadsheet =
        sheets.spreadsheets().get(feederConfig.spreadSheetId).execute()

    fun batchUpdate(request: BatchUpdateSpreadsheetRequest): BatchUpdateSpreadsheetResponse =
        sheets.spreadsheets().batchUpdate(feederConfig.spreadSheetId, request).execute()

}
