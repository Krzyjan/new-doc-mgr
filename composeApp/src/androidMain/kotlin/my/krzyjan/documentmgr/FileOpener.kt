package my.krzyjan.documentmgr

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

actual class FileOpener actual constructor() {
    private lateinit var context: Context
    fun setContext(context: Context) {
        this.context = context
    }
    actual fun openFile(fileUri: String, mimeType: String) {
        val uri = Uri.parse(fileUri)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, mimeType)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
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