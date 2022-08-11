package my.krzyjan.documentmgr

import io.nacular.doodle.application.Modules
import io.nacular.doodle.application.application
import my.krzyjan.documentmgr.NewDocumentManager

fun main() {
    application(modules = listOf(Modules.FontModule, Modules.PointerModule)) {
        NewDocumentManager()
    }
}