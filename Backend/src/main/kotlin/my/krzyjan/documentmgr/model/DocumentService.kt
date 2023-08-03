package my.krzyjan.documentmgr.model

interface DocumentService {
    suspend fun create(document: Document) : Int

    suspend fun read(id: Int) : Document?

    suspend fun update(id: Int, document: Document)

    suspend fun delete(id: Int)

}
