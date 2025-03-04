package my.krzyjan.documentmgr


import java.awt.Desktop
import java.io.File
import java.net.URI

actual class FileOpener actual constructor() {
    actual fun openFile(fileUri: String, mimeType: String) {
        try {
            val file = File(URI(fileUri))
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