package my.krzyjan.documentmgr

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

private lateinit var filePickerLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>

@Composable
actual fun registerPathChanger(onFileSelected: (String) -> Unit) {
    val context = LocalContext.current
    filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            uri?.let { getFileFromUri(context, it)?.name }?.let { onFileSelected(it) }
        }
    }
}

actual fun launchFilePicker() {

    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        type = "application/pdf"
    }
    filePickerLauncher.launch(intent)
}

private const val READ_BUFFER_SIZE = 4 * 1024

private fun getFileFromUri(context: Context, uri: Uri): File? {
    if (uri.scheme == "content") {
        val contentResolver = context.contentResolver
        val fileName = getFileName(contentResolver, uri) ?: return null
        val file = File(context.dataDir, fileName)

        try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    val buffer = ByteArray(READ_BUFFER_SIZE) // 4KB buffer
                    var read: Int
                    while (inputStream.read(buffer).also { read = it } != -1) {
                        outputStream.write(buffer, 0, read)
                    }
                    outputStream.flush()
                }
            }
            return file
        } catch (e: IOException) {
            Log.e("getFileFromUri", "${e.stackTrace}")
        }
    }
    return null
}

private fun getFileName(contentResolver: android.content.ContentResolver, uri: Uri): String? {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (displayNameIndex != -1) {
                    result = it.getString(displayNameIndex)
                }
            }
        }
    }
    return result
}

