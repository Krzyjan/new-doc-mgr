package my.krzyjan.documentmgr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import my.krzyjan.documentmgr.model.Document
import my.krzyjan.documentmgr.model.DocumentService
import org.kodein.di.DI
import org.kodein.di.instance

class ViewModel(val di: DI) {

    companion object ViewModelConstants {
        const val INITIAL_PAGE_SIZE = 20
    }

    private val documentService: DocumentService by di.instance<DocumentService>()

    var state: ModelState by mutableStateOf(setInitialState())
        private set

    fun onItemClicked(id: Int) {
        setState { copy(editingItemId = id) }
    }

    fun addItem() {
        if (state.newName.isNotBlank() && state.newPath.isNotBlank()) {
            setState {
                val newItem = Document(name = newName, path = newPath)

                newItem.id = documentService.create(newItem)

                copy(items = items + newItem, newName = "", newPath = "")
            }
        }
    }

    fun setName(text: String) {
        setState { copy(newName = text) }
    }

    fun setPath(text: String) {
        setState { copy(newPath = text) }
    }

    private fun setInitialState(): ModelState =
        ModelState(
            items = documentService.readPage(INITIAL_PAGE_SIZE) ?: emptyList()
        )

    private inline fun setState(update: ModelState.() -> ModelState) {
        state = state.update()
    }

    fun onEditorCloseClicked() {
        setState { copy(editingItemId = null) }
    }

    fun onEditorNameChanged(name: String) {
        setState {
            updateItem(id = requireNotNull(editingItemId)) { it.copy(name = name) }
        }
    }

    fun onEditorPathChanged(path: String) {
        setState {
            updateItem(id = requireNotNull(editingItemId)) { it.copy(path = path) }
        }
    }

    private fun ModelState.updateItem(id: Int, transformer: (Document) -> Document): ModelState =
        copy(items = items.updateItem(id = id, transformer = transformer))

    private fun List<Document>.updateItem(id: Int, transformer: (Document) -> Document): List<Document> =
        map { item -> if (item.id == id) transformer(item) else item }

    data class ModelState(
        val items: List<Document> = emptyList(),
        val editingItemId: Int? = null,
        val newName: String = "",
        val newPath: String = ""
    )
}

