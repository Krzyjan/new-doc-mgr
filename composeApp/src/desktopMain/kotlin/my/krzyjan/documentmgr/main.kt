package my.krzyjan.documentmgr

import my.krzyjan.documentmgr.model.DocumentService
import my.krzyjan.documentmgr.model.ExposedDocumentService
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val di = DI {
    bindSingleton <UserInterface> { UserInterface() }
    bindSingleton <DocumentService> { ExposedDocumentService() }
}

fun main() {
    val ui: UserInterface by di.instance()

    ui.invoke()
}