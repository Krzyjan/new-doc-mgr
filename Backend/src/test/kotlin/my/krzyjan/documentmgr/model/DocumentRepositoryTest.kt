package my.krzyjan.documentmgr.model

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class DocumentRepositoryTest() {

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var documentRepository: DocumentRepository

    companion object {
        private val LOGGER = LoggerFactory.getLogger(DocumentRepositoryTest::class.java)
    }

    @Test
    fun documentRepositoryIsInitialised() {
        val document = Document(name = "Gas Bill", path = "C:/Bills/Gas/202304.pdf")
        entityManager.persist(document)
        entityManager.flush()

        val documentFound = documentRepository.findByIdOrNull(document.id)
        LOGGER.info{"Document by id: ${document.id} $documentFound"}
        assertTrue(documentFound == document)
    }
}