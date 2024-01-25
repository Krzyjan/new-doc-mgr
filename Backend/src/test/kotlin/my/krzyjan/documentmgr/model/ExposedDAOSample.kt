package my.krzyjan.documentmgr.model

import my.krzyjan.documentmgr.model.ExposedDAOSample.TestConstants.ELECTRICITY_BILL
import my.krzyjan.documentmgr.model.ExposedDAOSample.TestConstants.ELECTRICITY_BILL_PATH
import my.krzyjan.documentmgr.model.ExposedDAOSample.TestConstants.GAS_BILL
import my.krzyjan.documentmgr.model.ExposedDAOSample.TestConstants.GAS_BILL_PATH
import my.krzyjan.documentmgr.model.ExposedDAOSample.TestConstants.NEW_ELECTRICITY_BILL_PATH
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeAll
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ExposedDAOSample {
    companion object {
        @JvmStatic
        @BeforeAll
        fun init() {
            //an example connection to H2 DB
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        }
    }

    object TestConstants {
        const val GAS_BILL = "Gas Bill 2023 April"
        const val GAS_BILL_PATH = "c:/Documents/Gas/2023"
        const val ELECTRICITY_BILL = "Electricity Bill 2023 May"
        const val ELECTRICITY_BILL_PATH = "c:/Documents/Electricity/2023"
        const val NEW_ELECTRICITY_BILL_PATH = "F:/Docs/2023/Electricity"

    }
    @Test
    fun testMethods() {
        transaction {
            // print sql to std-out
            addLogger(StdOutSqlLogger)

            SchemaUtils.drop(Documents)

            SchemaUtils.create (Documents)

            // insert new document. SQL: INSERT INTO DOCUMENTS ("NAME", "PATH") VALUES ('Gas Bill 2023 April', 'c:/Documents/Gas/2023')
            val gasBill = Document.new {
                name = GAS_BILL
                path = GAS_BILL_PATH
            }

            assertEquals(GAS_BILL, gasBill.name)
            assertEquals(GAS_BILL_PATH, gasBill.path)
            assertEquals(1, Document.count())

            // find the document by id.
            var readBill = Document.findById(gasBill.id)
            assertNotNull(readBill)
            assertEquals(GAS_BILL, readBill.name)
            assertEquals(GAS_BILL_PATH, readBill.path)

            // insert one more document
            Document.new {
                name = ELECTRICITY_BILL
                path = ELECTRICITY_BILL_PATH
            }

            // find document by column value. SQL: SELECT DOCUMENTS.ID, DOCUMENTS."NAME", DOCUMENTS."PATH" FROM DOCUMENTS WHERE DOCUMENTS."NAME" = 'Electricity Bill 2023 May'
            val documents = Document.find { Documents.name eq ELECTRICITY_BILL }
            assertNotNull(documents)
            assertEquals(1, documents.count())
            readBill = documents.elementAt(0)
            assertEquals(ELECTRICITY_BILL_PATH, readBill.path)

            // read all documents. SQL: SELECT DOCUMENTS.ID, DOCUMENTS."NAME", DOCUMENTS."PATH" FROM DOCUMENTS
            Document.all().map { println(it) }

            // update document. SQL: UPDATE DOCUMENTS SET "PATH"='F:/Docs/2023/Electricity' WHERE ID = 2
            readBill.path = NEW_ELECTRICITY_BILL_PATH
            readBill = Document.findById(readBill.id)
            assertNotNull(readBill)
            assertEquals(NEW_ELECTRICITY_BILL_PATH, readBill.path)

            // delete document. SQL: DELETE FROM DOCUMENTS WHERE DOCUMENTS.ID = 1
            gasBill.delete()
            readBill = Document.findById(gasBill.id)

            assertNull(readBill)
        }
    }

    object Documents: IntIdTable() {
        val name: Column<String> = varchar("name", 50)
        val path: Column<String> = varchar("path", 256)
    }

    class Document(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<Document>(Documents)

        var name by Documents.name
        var path by Documents.path

        override fun toString(): String {
            return ("$id:$name:$path")
        }
    }
}
