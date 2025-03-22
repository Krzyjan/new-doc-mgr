package my.krzyjan.documentmgr


import androidx.compose.runtime.Composable
import java.awt.Desktop
import java.io.File
import java.net.URI

actual class FileOpener actual constructor() {

    @Composable
    actual fun InitOpener() {}

    actual fun openFile(fileName: String, mimeType: String) {
        try {
            val file = File(URI(fileName))
            if (Desktop.isDesktopSupported()) {
                val desktop = Desktop.getDesktop()
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(file)
                } else {
                    println("Error: Opening files is not supported on this system.")
                    // Handle the case where opening is not supported (e.g., show an error message)
                }
            } else {
                println("Error: Desktop is not supported on this system.")
                // Handle the case where Desktop is not supported (e.g., show an error message)
            }
        } catch (e: Exception) {
            println("Error opening file: ${e.message}")
            // Handle the exception (e.g., show an error message)
        }
    }
}
