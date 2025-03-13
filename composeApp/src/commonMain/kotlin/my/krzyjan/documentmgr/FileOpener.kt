package my.krzyjan.documentmgr

import androidx.compose.runtime.Composable

expect class FileOpener() {
    @Composable
    fun initOpener()

    fun openFile(fileName: String, mimeType: String)
}
