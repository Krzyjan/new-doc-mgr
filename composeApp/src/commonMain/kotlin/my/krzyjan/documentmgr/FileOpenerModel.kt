package my.krzyjan.documentmgr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.io.File

class FileOpenerModel {
    private val fileOpener = FileOpener()
    private var fileUri by mutableStateOf("")
        private set
    private var mimeType by mutableStateOf("")
        private set
    private fun setFile(fileUri: String, mimeType: String) {
        this.fileUri = fileUri
        this.mimeType = mimeType
    }
    private fun openFile() {
        fileOpener.openFile(fileUri, mimeType)
    }
    fun handleFile(fileName: String) {
        setFile(File(fileName).toURI().toString(), "application/pdf")
        openFile()
    }
}
