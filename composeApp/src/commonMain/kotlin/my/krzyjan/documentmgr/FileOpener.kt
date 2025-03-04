package my.krzyjan.documentmgr

expect class FileOpener() {
    fun openFile(fileUri: String, mimeType: String)
}
