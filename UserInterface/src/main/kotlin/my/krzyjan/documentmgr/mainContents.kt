package my.krzyjan.documentmgr

import androidx.compose.foundation.clickable
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import my.krzyjan.documentmgr.model.Document

val di = DI {
    bindSingleton<RootStore> {
        RootStore()
    }
}

private val rootStore: RootStore by di.instance()

@Composable
private fun documentList() {
    TopAppBar(title = { Text(text = "Document List") })

    listContent()
}

@Composable
private fun listContent() {
    val model = remember { rootStore }

    Box {
        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            items(model.state.items) { it ->
                item(it)

                Divider()
            }
        }
    }
}

@Composable
private fun item(document: Document) {
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun newDocument() {
    val model = remember { rootStore }

    TopAppBar(title = { Text(text = "Add Document") })

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            value = model.state.newName,
            modifier = Modifier
                        .weight(weight = 1F)
                        .onKeyUp(key = Key.Enter, action = model::onAddItemClicked),
            onValueChange = model::onNameChanged,
            label = { Text(text = "Document Name") }
        )

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = model.state.newPath,
            modifier = Modifier
                .weight(weight = 1F)
                .onKeyUp(key = Key.Enter, action = model::onAddItemClicked),
            onValueChange = model::onPathChanged,
            label = { Text(text = "Document Path") }
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = model::onAddItemClicked) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}

@Composable
fun mainContent(modifier: Modifier) {
    Column(modifier) {
        newDocument()
        documentList()
    }
}
