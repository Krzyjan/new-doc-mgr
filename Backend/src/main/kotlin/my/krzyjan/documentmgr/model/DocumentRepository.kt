package my.krzyjan.documentmgr.model

import org.springframework.data.jpa.repository.JpaRepository

interface DocumentRepository: JpaRepository<Document, Long>