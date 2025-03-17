package my.krzyjan.documentmgr

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File

actual class FileOpener actual constructor() {
    private lateinit var context: Context

    @Composable
    actual fun initOpener() {
        this.context = LocalContext.current
    }
    actual fun openFile(fileName: String, mimeType: String) {
        val uri = getFileUri(context, File(context.filesDir, fileName))
        val intent = Intent(Intent.ACTION_VIEW).apply {
            type = mimeType
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
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
        }
    }
}

fun getFileUri(context: Context, file: File): Uri {
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider", // Same as in AndroidManifest.xml
        file
    )
}