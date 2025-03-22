package my.krzyjan.documentmgr

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File

actual class FileOpener actual constructor() {
    private lateinit var context: Context
    private var fileUri = mutableStateOf<Uri?>(null)
    private var mimeType = mutableStateOf<String?>(null)
    private val fileName = mutableStateOf<String?>(null)

    private lateinit var openFileLauncher: ActivityResultLauncher<Intent>

    @Composable
    actual fun InitOpener() {
        this.context = LocalContext.current

        openFileLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data?.data?.let { uri ->
                        openFileWithIntent(uri, mimeType.value)
                    }
                }
            }
        )

        // Composable function to launch the SAF intent.
        OpenFileSaf()
    }

    @Composable
    private fun OpenFileSaf() {
        LaunchedEffect(key1 = fileName.value) {
            val fileName = fileName.value ?: return@LaunchedEffect
            val mimeType = mimeType.value ?: return@LaunchedEffect
            val fileUri = createFileInDownloads(context, fileName) ?: return@LaunchedEffect
            this@FileOpener.fileUri.value = fileUri
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = mimeType
                putExtra(Intent.EXTRA_STREAM, fileUri)
            }
            openFileLauncher.launch(intent)
        }
    }

    actual fun openFile(fileName: String, mimeType: String) {
        this.mimeType.value = mimeType
        this.fileName.value = fileName
    }

    private fun openFileWithIntent(uri: Uri, mimeType: String?) {
        if (mimeType == null) {
            return
        }
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, mimeType)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val resolveInfoList = context.packageManager.queryIntentActivities(intent, 0)
        for (resolveInfo in resolveInfoList) {
            val packageName = resolveInfo.activityInfo.packageName
            context.grantUriPermission(
                packageName,
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            // Handle the case where no app can handle the intent
            Log.e("FileOpener", "No activity found to handle intent")
        }
    }

    private fun createFileInDownloads(context: Context, fileName: String): Uri? {
        val downloadsDir = getDocumentsFolder()
        val file = File(downloadsDir, fileName)
        return try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    Log.e("FileOpener", "Error creating file")
                    return null
                }
            }
            val fileUri = getFileUri(context, file)
            val contentResolver = context.contentResolver
            contentResolver.openOutputStream(fileUri)?.use { outputStream ->
                // Write data to the file
                val text = "This is an example file"
                outputStream.write(text.toByteArray())
            }
            fileUri
        } catch (e: Exception) {
            Log.e("FileOpener", "Error creating or writing to file", e)
            null
        }
    }

    fun getFileUri(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider", // Same as in AndroidManifest.xml
            file
        )
    }
}
