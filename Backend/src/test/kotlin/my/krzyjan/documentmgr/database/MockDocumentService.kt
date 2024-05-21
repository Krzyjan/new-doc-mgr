package my.krzyjan.documentmgr.database

import my.krzyjan.documentmgr.model.Document
import my.krzyjan.documentmgr.model.DocumentService

class MockDocumentService: DocumentService {

    class TableRow(val value: Document) {
         companion object {
            var highestId = 0
        }

        val id = highestId.inc()

        init {
            highestId = id
        }

    }
    companion object {
        private var id: Int = 0
        var rows = mutableListOf(
            TableRow(Document("202301 Gas Bill", "C:/Documents/2023/Gas/202301.pdf")),
            TableRow(Document("202302 Water Bill", "C:/Documents/2023/Water/202302.pdf")),
            TableRow(Document("202301 Electricity Bill", "C:/Documents/2023/Electricity/202301.pdf")),
            TableRow(Document("202303 Water Bill", "C:/Documents/2023/Water/202303.pdf")),
            TableRow(Document("202304 Gas Bill", "C:/Documents/2023/Gas/202304.pdf")),
            TableRow(Document("202305 Electricity Bill", "C:/Documents/2023/Electricity/202305.pdf")),
            TableRow(Document("202306 Water Bill", "C:/Documents/2023/Water/202306.pdf")),
            TableRow(Document("202307 Gas Bill", "C:/Documents/2023/Gas/202307.pdf")),
        )
    }

    fun showRows(): List<TableRow> = rows
    override fun create(document: Document): Int {
        val newRow = TableRow(value = document)
        rows.add(newRow)

        return newRow.id
    }

    override fun read(id: Int): Document? {
        return rows.first { it.id == id }.value
    }

    override fun readPage(size: Int, cursor: Long): List<Document>? {
        TODO("Not yet implemented")
    }

    override fun update(id: Int, document: Document) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int) {
        TODO("Not yet implemented")
    }
}
