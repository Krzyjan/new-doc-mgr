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

class UserInterface {
    fun invoke() = application {
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
}
