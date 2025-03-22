package my.krzyjan.documentmgr

import android.os.Environment
import java.io.File

fun getDocumentsFolder(): File? {
    return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
}
