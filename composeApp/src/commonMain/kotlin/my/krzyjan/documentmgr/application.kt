package my.krzyjan.documentmgr

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun application() {
    Surface(modifier = Modifier.fillMaxSize()) {
        MaterialTheme {
            mainView()
        }
    }
}
