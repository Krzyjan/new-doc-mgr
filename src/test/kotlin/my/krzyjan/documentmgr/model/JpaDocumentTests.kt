package my.krzyjan.documentmgr.model

import junit.framework.TestCase
import org.hibernate.cfg.Configuration
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase
import org.hibernate.testing.transaction.TransactionUtil
import org.hibernate.testing.transaction.TransactionUtil.doInHibernate
import org.junit.Test
import java.io.IOException
import java.util.Properties
import java.util.function.Supplier

class JpaDocumentTests: BaseCoreFunctionalTestCase() {
    private val properties: Properties
        @Throws(IOException::class)
        get() {
            val properties = Properties()
            properties.load(javaClass.classLoader.getResourceAsStream("hibernate.properties"))
            return properties
        }

    override fun getAnnotatedClasses(): Array<Class<*>> {
        return arrayOf(Document::class.java)
    }

    override fun configure(configuration: Configuration?) {
        super.configure(configuration)
        configuration?.properties = properties
    }

    @Test
    fun givenDocument_whenSaved_thenFound() {
        doInHibernate((Supplier { sessionFactory() }), TransactionUtil.HibernateTransactionConsumer { session ->
            val documentName = "Gas Bill"
            val documentPath = "c:/Documents/bills/20210214_gas_bill.pdf"

            val documentToSave = Document(0, documentName, documentPath)
            session.persist(documentToSave)

            val documentFound = session.find(Document::class.java, documentToSave.id)
            TestCase.assertEquals(documentName, documentFound.name)
            TestCase.assertEquals(documentPath, documentFound.path)
        })
    }
}