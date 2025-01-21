package my.krzyjan.documentmgr

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter


private lateinit var selectedFile: MutableState<String?>

@Composable
actual fun rememberFilePicker(): MutableState<String?> {
    selectedFile = remember { mutableStateOf(null) }
    return selectedFile
}

@Composable
actual fun launchFilePicker() {
    val fileChooser = JFileChooser().apply {
        fileSelectionMode = JFileChooser.FILES_ONLY
        val filter = FileNameExtensionFilter("Text Files", "txt", "text")
        fileFilter = filter
    }
    val result = fileChooser.showOpenDialog(null)
    if (result == JFileChooser.APPROVE_OPTION) {
        selectedFile.value = fileChooser.selectedFile.absolutePath
    }
}

