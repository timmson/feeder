package ru.timmson.feeder.cv

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import ru.timmson.feeder.cv.model.Fields
import ru.timmson.feeder.cv.sheet.SheetService

@ExtendWith(MockitoExtension::class)
class CVStoreShould {

    private lateinit var cvStore: CVStore

    @Mock
    private lateinit var sheetService: SheetService

    @BeforeEach
    fun setUp() {

        cvStore = CVStore(sheetService = sheetService)
    }

    @Test
    fun add() {
        cvStore.add(Fields("", "", "", "", "", ""))

        verify(sheetService, times(1)).add(any())
    }
}
