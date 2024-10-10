package my.krzyjan.documentmgr

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import my.krzyjan.documentmgr.model.Document
import my.krzyjan.documentmgr.model.DocumentService
import my.krzyjan.documentmgr.model.ExposedDocumentService
import org.jetbrains.exposed.sql.Database
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.compose.withDI

private val applicationConfig =
    ConfigLoaderBuilder.default().addResourceSource("/application-prod.conf").build()
        .loadConfigOrThrow<ApplicationConfig>()

private val rootStore: ViewModel = ViewModel(DI {
    bindSingleton<DocumentService> {
        val database: Database = Database.connect(
            url = "jdbc:h2:${applicationConfig.database.path};DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver",
            user = applicationConfig.database.user
        )
        ExposedDocumentService(database)
    }
})

@Composable
private fun documentListView() {
    TopAppBar(title = { Text(text = "Document List") })

    listContentView()
}

@Composable
private fun listContentView() {
    val model = remember { rootStore }

    Box {
        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            items(model.state.items) {
                itemView(it)
                Divider()
            }
        }
    }
}

@Composable
private fun itemView(document: Document) {
    Row(modifier = Modifier.clickable(onClick = { /* TODO */ })) {
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = AnnotatedString(document.name),
            modifier = Modifier.weight(1F).align(Alignment.CenterVertically),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = AnnotatedString(document.path),
            modifier = Modifier.weight(1F).align(Alignment.CenterVertically),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(8.dp))
    }
}

internal fun Modifier.onKeyUp(key: Key, action: () -> Unit): Modifier =
    onKeyEvent { event ->
        if ((event.type == KeyEventType.KeyUp) && (event.key == key)) {
            action()
            true
        } else {
            false
        }
    }

@Composable
private fun newDocumentView() {
    val model = remember { rootStore }

    TopAppBar(title = { Text(text = "Add Document") })

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            value = model.state.newName,
            modifier = Modifier
                .weight(weight = 1F)
                .moveOnFocusTab()
                .onKeyUp(key = Key.Enter, action = model::addItem),
            onValueChange = model::setName,
            label = { Text(text = "Document Name") }
        )

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = model.state.newPath,
            modifier = Modifier
                .weight(weight = 1F)
                .moveOnFocusTab()
                .onKeyUp(key = Key.Enter, action = model::addItem),
            onValueChange = model::setPath,
            label = { Text(text = "Document Path") }
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = model::addItem) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
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
fun rootContent(fillMaxSize: Modifier) {
    Column(fillMaxSize) {
        newDocumentView()
        documentListView()
    }
}

@Composable
fun mainView() = withDI(rootStore.di) {
    rootContent(Modifier.fillMaxSize())
}


