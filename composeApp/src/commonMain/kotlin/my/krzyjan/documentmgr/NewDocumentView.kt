package my.krzyjan.documentmgr

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

internal fun Modifier.onKeyUp(key: Key, action: () -> Unit): Modifier =
    onKeyEvent { event ->
        if ((event.type == KeyEventType.KeyUp) && (event.key == key)) {
            action()
            true
        } else {
            false
        }
    }

internal fun Modifier.moveOnFocusTab() = composed {
    val focusManager = LocalFocusManager.current
    onPreviewKeyEvent {
        if (it.type == KeyEventType.KeyDown && it.key == Key.Tab) {
            focusManager.moveFocus(
                if (it.isShiftPressed) FocusDirection.Previous else FocusDirection.Next
            )
            true
        } else {
            false
        }
    }
}

@Composable
fun newDocumentView(
    state: ViewModel.ModelState,
    setName: (String) -> Unit,
    setPath: (String) -> Unit,
    addItem: () -> Unit
)
{

    TopAppBar(title = { Text(text = "Add Document") })

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            value = state.newName,
            modifier = Modifier
                .weight(weight = 1F)
                .moveOnFocusTab()
                .onKeyUp(key = Key.Enter, action = addItem),
            onValueChange = setName,
            label = { Text(text = "Document Name") }
        )

        Spacer(modifier = Modifier.width(8.dp))

        RegisterPathChanger(setPath)

        OutlinedTextField(
            value = state.newPath,
            modifier = Modifier
                .weight(weight = 1F)
                .moveOnFocusTab()
                .onKeyUp(key = Key.Enter, action = addItem),
            onValueChange = setPath,
            label = { Text(text = "Document Path") },
            trailingIcon = {
                androidx.compose.material3.IconButton(onClick = {
                    launchFilePicker()
                }) {
                    androidx.compose.material3.Icon(
                        Icons.Filled.FileOpen,
                        contentDescription = "Select File"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = addItem) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}

