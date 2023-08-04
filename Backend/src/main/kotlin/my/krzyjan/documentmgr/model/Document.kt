package my.krzyjan.documentmgr.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

data class Document(val name: String, val path: String)

class ExposedDocumentService(database: Database):DocumentService {
    object Documents : Table() {
        val id = integer("id").autoIncrement()
        val name = varchar("name", length = 128)
        val path = varchar("path", length = 1028)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(Documents)
        }
    }

    override fun create(document: Document): Int = runCreate(document)

    override fun read(id: Int) : Document? {
        return runRead(id)
    }

    override fun update(id: Int, document: Document) {
        runUpdate(id, document)
    }

    override fun delete(id: Int) {
        runDelete(id)
    }

    private suspend fun <T> dbQuery(block: suspend  () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    private fun runCreate(document: Document) = runBlocking {
        dbQuery {
            Documents.insert {
                it[name] = document.name
                it[path] = document.path
            } [Documents.id]
        }
    }

    private fun runRead(id: Int) = runBlocking {
        return@runBlocking dbQuery {
            Documents.select { Documents.id eq id}
                .map { Document(it[Documents.name], it[Documents.path]) }
                .singleOrNull()
        }
    }

    private fun runUpdate(id: Int, document: Document) {
        runBlocking {
            dbQuery {
                Documents.update( {Documents.id eq id}) {
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
