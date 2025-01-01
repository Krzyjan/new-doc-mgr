package my.krzyjan.documentmgr

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

expect class FilePicker() {
    @Composable
    fun rememberFilePicker(): MutableState<String?>

    fun launchFilePicker()
}
