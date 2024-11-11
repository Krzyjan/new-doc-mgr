package my.krzyjan.documentmgr

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import my.krzyjan.documentmgr.model.DocumentService
import my.krzyjan.documentmgr.model.ExposedDocumentService
import org.jetbrains.exposed.sql.Database
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.compose.withDI

private val applicationConfig =
    ConfigLoaderBuilder.default().addResourceSource("/application-dev.conf").build()
        .loadConfigOrThrow<ApplicationConfig>()

private val rootStore: ViewModel = ViewModel(DI {
    bindSingleton<DocumentService> {
        val database: Database = Database.connect(
            url = "jdbc:h2:${applicationConfig.database.path};${applicationConfig.database.urlExtra};${applicationConfig.database.debug}",
            driver = "org.h2.Driver",
            user = applicationConfig.database.user
        )
        ExposedDocumentService(database)
    }
})

@Composable
fun mainView() = withDI(rootStore.di) {
    Column(Modifier.fillMaxSize()) {
        documentListView(rootStore)
        newDocumentView(rootStore)
    }
}


