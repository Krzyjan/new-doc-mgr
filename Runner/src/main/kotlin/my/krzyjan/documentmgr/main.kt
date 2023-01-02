package my.krzyjan.documentmgr

import io.nacular.doodle.application.Modules
import io.nacular.doodle.application.application
import org.kodein.di.DI

fun main() {
    application(modules = listOf(
        Modules.FontModule, Modules.PointerModule,
        DI.Module(name = "AppModule") {
        }
    )) {
        NewDocumentManager()
    }
}