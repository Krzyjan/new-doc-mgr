package my.krzyjan.documentmgr.model

import my.krzyjan.documentmgr.model.DocumentServiceTest.TestConstants.DOCUMENT_NAME
import my.krzyjan.documentmgr.model.DocumentServiceTest.TestConstants.DOCUMENT_PATH
import my.krzyjan.documentmgr.model.DocumentServiceTest.TestConstants.NEW_DOCUMENT_NAME
import org.jetbrains.exposed.sql.Database
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DocumentServiceTest {

    object TestConstants {
        const val DOCUMENT_NAME = "Gas Bill 2023 April"
        const val NEW_DOCUMENT_NAME = "Gas Bill 2023/04"
        const val DOCUMENT_PATH = "C:/Document/Home/Bill2/2023"
    }
    @Test
    fun testMethods() {
        val database = Database.connect(
            url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            user = "root",
            driver = "org.h2.Driver",
            password = ""
        )

        val documentService:DocumentService = ExposedDocumentService(database)

        /*
        ** Test create
         */

        val id = documentService.create(Document(DOCUMENT_NAME, DOCUMENT_PATH))

        var storedDocument = documentService.read(id)

        assertNotNull(storedDocument)

        assertEquals(DOCUMENT_NAME, storedDocument.name)

        /*
        ** Test update
         */

        documentService.update(id, Document(NEW_DOCUMENT_NAME, DOCUMENT_PATH))

        storedDocument = documentService.read(id)

        assertNotNull(storedDocument)

        assertEquals(NEW_DOCUMENT_NAME, storedDocument.name)

        /*
        ** Test delete
         */
        documentService.delete(id)

        storedDocument = documentService.read(id)

        assertNull(storedDocument)
    }
}
