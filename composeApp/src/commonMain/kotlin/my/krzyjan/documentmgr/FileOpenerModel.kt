package my.krzyjan.documentmgr

import java.io.File

class FileOpenerModel {
    val fileOpener = FileOpener()

    fun handleFile(fileName: String) {
        fileOpener.openFile(fileName, "application/pdf")
    }
}
