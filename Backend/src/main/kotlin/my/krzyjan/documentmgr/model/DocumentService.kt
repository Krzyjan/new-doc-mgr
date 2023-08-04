package my.krzyjan.documentmgr.model

interface DocumentService {
    fun create(document: Document) : Int

    fun read(id: Int) : Document?

    fun update(id: Int, document: Document)

    fun delete(id: Int)
}
