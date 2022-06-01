package my.krzyjan.documentmgr.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "document")
class Document(
    @Id @GeneratedValue var id: Long = 0,
    var name: String = "",
    val path: String = ""
)