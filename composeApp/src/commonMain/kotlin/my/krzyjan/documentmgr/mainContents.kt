package my.krzyjan.documentmgr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
    ConfigLoaderBuilder.default().addResourceSource("/application-dev.conf").build()
        .loadConfigOrThrow<ApplicationConfig>()

private val rootStore: ViewModel = ViewModel(DI {
    bindSingleton<DocumentService> {
        val database: Database = Database.connect(
            url = "jdbc:h2:${applicationConfig.database.path};" +
                "${applicationConfig.database.urlExtra};" +
                    applicationConfig.database.debug,
            driver = "org.h2.Driver",
            user = applicationConfig.database.user
        )
        ExposedDocumentService(database)
    }
})

@Composable
fun mainView() = withDI(rootStore.di) {
    val model = remember { rootStore }
    val fileOpenerModel = FileOpenerModel()
    val state = model.state

    Column(Modifier.background(MaterialTheme.colors.background)) {
        documentListView(
            state.items,
            model::onItemClicked,
            model::onDeleteItemClicked,
            fileOpenerModel::handleFile
        )
        newDocumentView(state, model::setName, model::setPath, model::addItem)
    }

    state.editingItem?.also { item ->
        EditDialog(
            item = item,
            model::onEditorCloseClicked,
            model::onEditorNameChanged,
            model::onEditorPathChanged
        )
    }

    state.deletedItem?.also { item ->
        DeleteDialog(
            name = item.name,
            model::onDeleteItemConfirmed,
            model::onDeleteItemCancelled
        )
    }
}

@Composable
fun DeleteDialog(
    name: String,
    onDeleteItemConfirmed: () -> Unit,
    onDeleteItemCancelled: () -> Unit
) {
    Dialog(onDismissRequest = onDeleteItemCancelled) {
        Card(elevation = 8.dp) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .height(IntrinsicSize.Min)
            ) {
                Box(modifier = Modifier.weight(1F)) {
                    ProvideTextStyle(MaterialTheme.typography.caption) {
                        Text(text = "Delete $name document?")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Button(
                        onClick = onDeleteItemConfirmed,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text("Yes")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = onDeleteItemCancelled,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                    ) {
                        Text("No", fontWeight = FontWeight(1000))
                    }
                }
            }
        }
    }
}

@Composable
internal fun EditDialog(
    item: Document,
    onEditorCloseClicked: () -> Unit,
    onEditorNameChanged: (String) -> Unit,
    onEditorPathChanged: (String) -> Unit
) {
    Dialog(onDismissRequest = onEditorCloseClicked) {
        Card(elevation = 8.dp) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .height(IntrinsicSize.Min)
            ) {
                Box(modifier = Modifier.weight(1F)) {
                    Column(horizontalAlignment = Alignment.Start) {
                        TextField(
                            value = item.name,
                            label = { Text("Document Name") },
                            onValueChange = onEditorNameChanged
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = item.path,
                            label = { Text("Document Path") },
                            onValueChange = onEditorPathChanged,
                            trailingIcon = {
                                IconButton(onClick = {
                                    launchFilePicker()
                                }) {
                                    Icon(
                                        Icons.Filled.FileOpen,
                                        contentDescription = "Select File"
                                    )
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        registerPathChanger(onEditorPathChanged)

                        Button(
                            onClick = onEditorCloseClicked,
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Done")
                        }
                    }
                }
            }
        }
    }
}

private val ViewModel.ModelState.editingItem: Document?
    get() = editingItemId?.let(items::firstById)

private val ViewModel.ModelState.deletedItem: Document?
    get() = itemToDeleteId?.let(items::firstById)

private fun List<Document>.firstById(id: Int): Document? =
    firstOrNull { it.id == id }





