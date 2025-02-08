package my.krzyjan.documentmgr

import androidx.compose.runtime.Composable

@Composable
expect fun registerPathChanger(changeDocumentPath: (String) -> Unit)

expect fun launchFilePicker()

