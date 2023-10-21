package my.krzyjan.documentmgr.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DocumentServiceTest {

    companion object TestConstants {
        const val DOCUMENT_NAME = "Gas Bill 2023 April"
        const val NEW_DOCUMENT_NAME = "Gas Bill 2023/04"
        const val DOCUMENT_PATH = "C:/Document/Home/Bill2/2023"

        val someDocuments = listOf(
            Document("202301 Gas Bill", "C:/Documents/2023/Gas/202301.pdf"),
            Document("202302 Water Bill", "C:/Documents/2023/Water/202302.pdf"),
            Document("202301 Electricity Bill", "C:/Documents/2023/Electricity/202301.pdf"),
            Document("202303 Water Bill", "C:/Documents/2023/Water/202303.pdf"),
            Document("202304 Gas Bill", "C:/Documents/2023/Gas/202304.pdf"),
            Document("202305 Electricity Bill", "C:/Documents/2023/Electricity/202305.pdf"),
            Document("202306 Water Bill", "C:/Documents/2023/Water/202306.pdf"),
            Document("202307 Gas Bill", "C:/Documents/2023/Gas/202307.pdf"),
        )
    }

    @Test
    fun testCRUD() {
        val documentService: DocumentService = ExposedDocumentService()

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

    @Test
    fun testPaging() {
        val documentService: DocumentService = ExposedDocumentService()

        /*
        ** Test paging
         */
        someDocuments.forEach{documentService.create(it)}

        val pageSize = 5
        var cursor = 0L
        var documentsLeft = someDocuments.size

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
