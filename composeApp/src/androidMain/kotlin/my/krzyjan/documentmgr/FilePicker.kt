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
actual fun RegisterPathChanger(onFileSelected: (String) -> Unit) {
    val context = LocalContext.current
    filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            uri?.let { downloadFile(context, it)?.name}?.let { onFileSelected(it) }
        }
    }
}

actual fun launchFilePicker() {

    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        type = "application/pdf"
    }
    filePickerLauncher.launch(intent)
}

private const val READ_BUFFER_SIZE = 8 * 1024

@Throws(IOException::class)
private fun downloadFile(context: Context, uri: Uri): File? {
    require(uri.scheme == "content") { "Unsupported URI scheme: ${uri.scheme}" }
    val contentResolver = context.contentResolver
    val (fileName, fileSize) = requireNotNull(getFileName(contentResolver, uri)) { "Failed to get file name from URI" }
    val file = fileName?.let { File(getDocumentsFolder(), it) }

    // The file may already be in the shared Documents folder
    file?.let  {
        if (it.exists() && it.length() == fileSize) {
            if (it.length() == fileSize) {
                return it
            } else {
                throw IOException("existing $fileName is the wrong size: $fileSize")
            }
        }
    }

    // On Android we store the document in the shared Documents folder
    try {
        contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream, bufferSize = READ_BUFFER_SIZE)
            }
        }
        return file
    } catch (e: IOException) {
        Log.e("getFileFromUri", "${e.stackTrace}")
        throw e
    }
}

/*
** This just gets a file name without a full path. Using full paths is not really supported on Android.
 */
private fun getFileName(contentResolver: android.content.ContentResolver, uri: Uri): Pair<String?, Long>? {
    var name: String? = null
    var fileSize: Long = 0

    require(uri.scheme == "content") { "Unsupported URI scheme: ${uri.scheme}" }
    val cursor = contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (displayNameIndex != -1) {
                name = it.getString(displayNameIndex)
            }
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            fileSize = cursor.getLong(sizeIndex)

        }
    }

    if (name != null) {
        return Pair(name, fileSize)
    } else {
        return null
    }
}
