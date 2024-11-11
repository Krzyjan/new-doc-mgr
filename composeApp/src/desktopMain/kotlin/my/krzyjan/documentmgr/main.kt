package my.krzyjan.documentmgr

import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Document Manager",
        state = rememberWindowState(
            position = WindowPosition(alignment = Alignment.Center)
        ),
    ) {
        mainView()
    }
}
