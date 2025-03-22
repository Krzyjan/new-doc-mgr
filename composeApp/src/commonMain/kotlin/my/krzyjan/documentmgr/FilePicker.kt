package my.krzyjan.documentmgr

import androidx.compose.runtime.Composable

@Composable
expect fun RegisterPathChanger(onFileSelected: (String) -> Unit)

expect fun launchFilePicker()

