package my.krzyjan.documentmgr.database

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import my.krzyjan.documentmgr.ApplicationConfig
import my.krzyjan.documentmgr.model.ExposedDocumentService
import my.krzyjan.documentmgr.model.TestConstants
import org.jetbrains.exposed.sql.Database

fun main() {
    val config = ConfigLoaderBuilder.default().addResourceSource("/application-prod.conf").build()
        .loadConfigOrThrow<ApplicationConfig>()

    val database: Database = Database.connect(
        url = "jdbc:h2:${config.database.path};DB_CLOSE_DELAY=-1",
        driver = "org.h2.Driver",
        user = config.database.user
    )

    val documentService = ExposedDocumentService(database)

    TestConstants.someDocuments.forEach { documentService.create(it) }

    documentService.readPage(5)?.forEach { println(it) }
}
