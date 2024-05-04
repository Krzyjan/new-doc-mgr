package my.krzyjan.documentmgr.model

import org.jetbrains.exposed.sql.Database
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DocumentServiceTest {


    @Test
    fun testCRUD() {
        runCRUDTest(ExposedDocumentService.DBSettings.defaultDb)
    }

    private val nonDefaultDatabase: Database = Database.connect(
        url = "jdbc:h2:mem:fred;DB_CLOSE_DELAY=-1",
        driver = "org.h2.Driver"
    )

    @Test
    fun testCRUDwithNonDefDb() {
        runCRUDTest(nonDefaultDatabase)
    }

    private fun runCRUDTest(database: Database) {
        val documentService: DocumentService = ExposedDocumentService(database)
        /*
        ** Test create
         */

        val id = documentService.create(Document(TestConstants.DOCUMENT_NAME, TestConstants.DOCUMENT_PATH))

        var storedDocument = documentService.read(id)

        assertNotNull(storedDocument)

        assertEquals(TestConstants.DOCUMENT_NAME, storedDocument.name)

        /*
        ** Test update
         */

        documentService.update(id, Document(TestConstants.NEW_DOCUMENT_NAME, TestConstants.DOCUMENT_PATH))

        storedDocument = documentService.read(id)

        assertNotNull(storedDocument)

        assertEquals(TestConstants.NEW_DOCUMENT_NAME, storedDocument.name)

        /*
        ** Test delete
         */
        documentService.delete(id)

        storedDocument = documentService.read(id)

        assertNull(storedDocument)

    }

    @Test
    fun testPaging() {
        val documentService: DocumentService = ExposedDocumentService()

        /*
        ** Test paging
         */
        TestConstants.someDocuments.forEach{documentService.create(it)}

        val pageSize = 5
        var cursor = 0L
        var documentsLeft = TestConstants.someDocuments.size

        var pageOfDocuments = documentService.readPage(pageSize)

        assertNotNull(pageOfDocuments)
        assertEquals(pageSize, pageOfDocuments.size)

        cursor += pageSize
        documentsLeft -= pageOfDocuments.size

        pageOfDocuments = documentService.readPage(pageSize, cursor)
        assertNotNull(pageOfDocuments)
        assertEquals(documentsLeft, pageOfDocuments.size)

    }
}
