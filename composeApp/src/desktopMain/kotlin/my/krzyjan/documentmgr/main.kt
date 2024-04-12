package my.krzyjan.documentmgr

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import my.krzyjan.documentmgr.model.DocumentService
import my.krzyjan.documentmgr.model.ExposedDocumentService
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val di = DI {
    bindSingleton <DocumentService> { ExposedDocumentService() }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Document Manager",
        state = rememberWindowState(
            position = WindowPosition(alignment = Alignment.Center)
        ),
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            MaterialTheme {
                mainView()
            }
        }
    }
}