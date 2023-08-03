package my.krzyjan.documentmgr.model

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

data class Document(val name: String, val path: String)

class ExposedDocumentService(private val database: Database):DocumentService {
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

    private suspend fun <T> dbQuery(block: suspend  () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    override suspend fun create(document: Document): Int = dbQuery {
        Documents.insert {
            it[name] = document.name
            it[path] = document.path
        } [Documents.id]
    }

    override suspend fun read(id: Int) : Document? {
        return dbQuery {
            Documents.select { Documents.id eq id}
                .map { Document(it[Documents.name], it[Documents.path]) }
                .singleOrNull()
        }
    }

    override suspend fun update(id: Int, document: Document) {
        dbQuery {
            Documents.update( {Documents.id eq id}) {
                it[name] = document.name
                it[path] = document.path
            }
        }
    }

    override suspend fun delete(id: Int) {
        dbQuery {
            Documents.deleteWhere { Documents.id.eq(id) }
        }
    }
}
