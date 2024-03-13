package my.krzyjan.documentmgr.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPagedDocumentsTest {

    private val testDispatcher = StandardTestDispatcher()

    private val documentService = ExposedDocumentService()

    @BeforeEach
    fun setUp() {
        TestConstants.someDocuments.forEach{documentService.create(it)}
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun invoke() = runTest {
    }

}