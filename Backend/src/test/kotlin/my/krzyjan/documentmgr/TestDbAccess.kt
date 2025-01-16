package my.krzyjan.documentmgr

import my.krzyjan.documentmgr.model.Document
import my.krzyjan.documentmgr.model.DocumentService
import my.krzyjan.documentmgr.model.ExposedDocumentService
import org.jetbrains.exposed.sql.Database

val someDocuments = listOf(
    Document(1, "202301 Gas Bill", "C:/Documents/2023/Gas/202301.pdf"),
    Document(2, "202302 Water Bill", "C:/Documents/2023/Water/202302.pdf"),
    Document(3, "202301 Electricity Bill", "C:/Documents/2023/Electricity/202301.pdf"),
    Document(4, "202303 Water Bill", "C:/Documents/2023/Water/202303.pdf"),
    Document(5, "202304 Gas Bill", "C:/Documents/2023/Gas/202304.pdf"),
    Document(6, "202305 Electricity Bill", "C:/Documents/2023/Electricity/202305.pdf"),
    Document(7, "202306 Water Bill", "C:/Documents/2023/Water/202306.pdf"),
    Document(8, "202307 Gas Bill", "C:/Documents/2023/Gas/202307.pdf"),
)


fun main() {
    val database: Database = Database.connect(
        url = "jdbc:h2:./test;DB_CLOSE_DELAY=-1",
        driver = "org.h2.Driver",
        user = "admin"
    )

    val documentService: DocumentService = ExposedDocumentService(database)

    // read what's there
    val pageSize = 5
    var cursor = 0L

    var pageOfDocuments = documentService.readPage(pageSize)
    while (pageOfDocuments != null && pageOfDocuments.isNotEmpty()) {
        cursor += pageOfDocuments.size
        pageOfDocuments.forEach { println(it) }
        pageOfDocuments = documentService.readPage(pageSize, cursor)
    }

    someDocuments.forEach { documentService.create(it) }

}