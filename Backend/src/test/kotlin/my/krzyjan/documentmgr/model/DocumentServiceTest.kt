package my.krzyjan.documentmgr.model

import kotlinx.coroutines.runBlocking
import my.krzyjan.documentmgr.model.DocumentServiceTest.TestConstants.DOCUMENT_NAME
import my.krzyjan.documentmgr.model.DocumentServiceTest.TestConstants.DOCUMENT_PATH
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.reflect.KSuspendFunction1
import kotlin.test.Test
import kotlin.test.assertEquals

class DocumentServiceTest {

    object TestConstants {
        const val DOCUMENT_NAME = "Gas Bill 2023 April"
        const val DOCUMENT_PATH = "C:/Document/Home/Bill2/2023"
    }
    @Test
    fun testMethods() {
        val document = Document(DOCUMENT_NAME, DOCUMENT_PATH)
        val database = Database.connect(
            url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            user = "root",
            driver = "org.h2.Driver",
            password = ""
        )

        val documentService:DocumentService = ExposedDocumentService(database)

        transaction {
            SchemaUtils.create(ExposedDocumentService.Documents)
        }

        val id = runCreate(documentService::create, document)

        val storedDocument = runRead(documentService::read, id)

        if (storedDocument != null) {
            assertEquals(DOCUMENT_NAME, storedDocument.name)
        }
    }

    private fun runCreate(method: KSuspendFunction1<Document, Int>, document: Document) : Int = runBlocking {
        method(document)
    }

    private fun runRead(method: KSuspendFunction1<Int, Document?>, id: Int) : Document? = runBlocking {
        method(id)
    }

}
