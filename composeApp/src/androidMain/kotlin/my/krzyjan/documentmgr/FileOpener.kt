package my.krzyjan.documentmgr

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File

actual class FileOpener actual constructor() {
    private lateinit var context: Context

    @Composable
    actual fun InitOpener() {
        this.context = LocalContext.current
    }

    actual fun openFile(fileName: String, mimeType: String) {
        val file = File(getDocumentsFolder(), fileName)
        val fileUri = getFileUri(context, file)
        openFileWithIntent(fileUri, mimeType)
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

    private fun getFileUri(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider", // Same as in AndroidManifest.xml
            file
        )
    }
}
