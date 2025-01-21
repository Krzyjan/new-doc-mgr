package my.krzyjan.documentmgr

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

private lateinit var selectedFile: MutableState<String?>

@Composable
actual fun rememberFilePicker(): MutableState<String?> {
    selectedFile = remember { mutableStateOf(null) }
    return selectedFile
}

@Composable
actual fun launchFilePicker() {
    val context = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            selectedFile.value = uri?.let { getPathFromUri(context, it) }
        }
    }

    val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
        type = "*/*"
    }
    filePickerLauncher.launch(intent)
}

private fun getPathFromUri(context: android.content.Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val columnIndex =
                it.getColumnIndexOrThrow(android.provider.MediaStore.Files.FileColumns.DATA)
            if (columnIndex != -1) {
                return it.getString(columnIndex)
            }
        }
    }
    return null
}

