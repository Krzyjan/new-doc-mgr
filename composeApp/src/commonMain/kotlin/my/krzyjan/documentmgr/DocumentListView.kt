package my.krzyjan.documentmgr

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import my.krzyjan.documentmgr.model.Document

@Composable
fun documentListView(documents: List<Document>, onItemClicked: (Int) -> Unit) {
    TopAppBar(title = { Text(text = "Document List") })

    listContentView(documents, onItemClicked)
}

@Composable
private fun listContentView(documents: List<Document>, onItemClicked: (Int) -> Unit) {
    Box {
        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            items(documents) { document ->
                itemView(document) { document.id?.let { id -> (onItemClicked)(id) } }
                Divider()
            }
        }
    }
}

@Composable
private fun itemView(document: Document, onItemClicked: () -> Unit) {
    Row(modifier = Modifier.clickable(onClick = onItemClicked)) {
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

