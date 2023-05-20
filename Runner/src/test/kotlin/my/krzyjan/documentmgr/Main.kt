package my.krzyjan.documentmgr

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val di = DI {
    bindSingleton <UserInterface> { UserInterface() }
}

fun main() {
    val ui: UserInterface by di.instance()

    ui.invoke()
}