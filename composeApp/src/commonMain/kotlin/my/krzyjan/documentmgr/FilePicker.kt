package my.krzyjan.documentmgr

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
expect fun rememberFilePicker(): MutableState<String?>

expect fun launchFilePicker()

