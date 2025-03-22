package my.krzyjan.documentmgr

import androidx.compose.runtime.Composable
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter


private lateinit var documentPathChanger: (String) -> Unit

@Composable
actual fun RegisterPathChanger(onFileSelected: (String) -> Unit) {
    documentPathChanger = onFileSelected
}

actual fun launchFilePicker() {
    val fileChooser = JFileChooser().apply {
        fileSelectionMode = JFileChooser.FILES_ONLY
        val filter = FileNameExtensionFilter("Acrobat Reader Files", "pdf", "text")
        fileFilter = filter
    }
    val result = fileChooser.showOpenDialog(null)
    if (result == JFileChooser.APPROVE_OPTION) {
        documentPathChanger(fileChooser.selectedFile.absolutePath)
    }
}

