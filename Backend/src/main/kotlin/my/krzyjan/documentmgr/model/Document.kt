package my.krzyjan.documentmgr.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

data class Document(var id: Int? = null, val name: String, var path: String)

class ExposedDocumentService(db: Database = defaultDb) : DocumentService {
    object Documents : Table() {
        var id = integer("id").autoIncrement()
        val name = varchar("name", length = 128)
        val path = varchar("path", length = 1028)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(db) {
            SchemaUtils.create(Documents)
        }
    }

    override fun create(document: Document): Int = runCreate(document)

    override fun read(id: Int): Document? {
        return runRead(id)
    }

    override fun readPage(size: Int, cursor: Long): List<Document>? {
        return runPageSelect(size, cursor)
    }

    override fun update(id: Int, document: Document) {
        runUpdate(id, document)
    }

    override fun delete(id: Int) {
        runDelete(id)
    }

    companion object DbRunner {
        val defaultDb by lazy {
            Database.connect(
                url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
                user = "root",
                driver = "org.h2.Driver",
                password = ""
            )
        }
        private suspend fun <T> dbQuery(block: suspend () -> T): T =
            newSuspendedTransaction(Dispatchers.IO) { block() }

        private fun runPageSelect(pageSize: Int, cursor: Long) = runBlocking {
            val page = mutableListOf<Document>()
            dbQuery {
                Documents.selectAll().limit(pageSize, cursor)
                    .forEach {
                        page.add(Document(id = it[Documents.id], name = it[Documents.name], path = it[Documents.path]))
                    }
            }
            return@runBlocking page
        }

        private fun runCreate(document: Document) = runBlocking {
            dbQuery {
                Documents.insert {
                    it[name] = document.name
                    it[path] = document.path
                }[Documents.id]
            }
        }

        private fun runRead(id: Int) = runBlocking {
            return@runBlocking dbQuery {
                Documents.select { Documents.id eq id }
                    .map { Document(name = it[Documents.name], path = it[Documents.path]) }
                    .singleOrNull()
            }
        }

        private fun runUpdate(id: Int, document: Document) {
            runBlocking {
                dbQuery {
                    Documents.update({ Documents.id eq id }) {
                        it[name] = document.name
                        it[path] = document.path
                    }
                }
            }
        }

        private fun runDelete(id: Int) {
            runBlocking {
                dbQuery {
                    Documents.deleteWhere { Documents.id.eq(id) }
                }
            }
        }
    }
}
