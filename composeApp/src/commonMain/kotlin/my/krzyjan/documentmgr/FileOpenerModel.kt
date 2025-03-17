package my.krzyjan.documentmgr

class FileOpenerModel {
    val fileOpener = FileOpener()

    fun handleFile(fileName: String) {
        fileOpener.openFile(fileName, "application/pdf")
    }
}
