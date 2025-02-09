package my.krzyjan.documentmgr

import androidx.compose.runtime.Composable

@Composable
expect fun registerPathChanger(onFileSelected: (String) -> Unit)

expect fun launchFilePicker()

