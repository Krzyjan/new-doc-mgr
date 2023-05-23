package my.krzyjan.documentmgr

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

private enum class ScrollableType {
    ScrollableColumn,
    LazyColumn,
}

@Composable
private fun TextFieldInScrollableColumn() {
    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        repeat(50) { index ->
            DemoTextField(index)
        }
    }
}

@Composable
private fun TextFieldInLazyColumn() {
    LazyColumn {
        items(50) { index ->
            DemoTextField(index)
        }
    }
}

@Composable
private fun DemoTextField(index: Int) {
    var text by rememberSaveable { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
        leadingIcon = { Text(index.toString()) },
        modifier = Modifier
            .padding(4.dp)
            .border(1.dp, Color.Black)
            .fillMaxWidth()
    )
}

@Composable
private fun documentList() {
    var scrollableType by remember { mutableStateOf(ScrollableType.values().first()) }

    Column() {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = scrollableType == ScrollableType.ScrollableColumn,
                onClick = { scrollableType = ScrollableType.ScrollableColumn }
            )
            Text("Scrollable column")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = scrollableType == ScrollableType.LazyColumn,
                onClick = { scrollableType = ScrollableType.LazyColumn }
            )
            Text("LazyColumn")
        }

        when (scrollableType) {
            ScrollableType.ScrollableColumn -> TextFieldInScrollableColumn()
            ScrollableType.LazyColumn -> TextFieldInLazyColumn()
        }
    }
}

@Composable
private fun newDocument() {
    val documentNameLabel = remember { mutableStateOf(TextFieldValue("")) }
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        TextField(
            value = documentNameLabel.value,
            placeholder = { Text("document name") },
            modifier = Modifier.alignBy(LastBaseline).weight(1.0f),
            onValueChange = { it -> documentNameLabel.value = it }
        )
    }
}

@Composable
fun mainContent(modifier: Modifier) {
    Column(modifier) {
        newDocument()
        documentList()
    }
}
