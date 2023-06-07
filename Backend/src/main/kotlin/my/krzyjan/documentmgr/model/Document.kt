package my.krzyjan.documentmgr.model

import jakarta.persistence.*

/*
** Everything here is explicitly defined as open to confirm to JPA specification
 */
@Entity
@Table(name = "document")
open class Document(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long = 0,

    @Column(name = "name", nullable = false)
    open var name: String = "",

    @Column(name = "path", nullable = false)
    open val path: String = ""
)