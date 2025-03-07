package my.krzyjan.documentmgr

import java.io.File

class FileOpenerModel {
    private val fileOpener = FileOpener()

    fun handleFile(fileName: String) {
        fileOpener.openFile(File(fileName).toURI().toString(), "application/pdf")
    }
}
