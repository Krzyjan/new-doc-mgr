package my.krzyjan.documentmgr.database

import my.krzyjan.documentmgr.model.ExposedDocumentService
import my.krzyjan.documentmgr.model.TestConstants
import org.jetbrains.exposed.sql.Database


fun main() {
    val database:Database = Database.connect(
        url = "jdbc:h2:mem:george;DB_CLOSE_DELAY=-1",
        driver = "org.h2.Driver",
        user = "root"
    )

    val documentService = ExposedDocumentService(database)

    TestConstants.someDocuments.forEach { documentService.create(it) }

    documentService.readPage(5)?.forEach { println(it) }
}