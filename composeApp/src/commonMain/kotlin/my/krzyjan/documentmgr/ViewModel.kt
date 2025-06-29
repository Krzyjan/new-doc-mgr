package my.krzyjan.documentmgr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import my.krzyjan.documentmgr.model.Document
import my.krzyjan.documentmgr.model.DocumentService

class ViewModel(private val documentService: DocumentService) {

    companion object ViewModelConstants {
        const val INITIAL_PAGE_SIZE = 20
    }

    var state: ModelState by mutableStateOf(setInitialState())
        private set

    fun onItemClicked(id: Int) {
        setState { copy(editingItemId = id) }
    }

    fun onDeleteItemClicked(id: Int) {
        setState { copy(itemToDeleteId = id) }
    }

    fun onDeleteItemConfirmed() {
        setState {
            if (itemToDeleteId != null) {
                documentService.delete(itemToDeleteId)
            }
            copy(items = items.filterNot { it.id == itemToDeleteId }, itemToDeleteId = null)
        }
    }

    fun onDeleteItemCancelled() {
        setState { copy(itemToDeleteId = null) }
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
        setState {
            if (editingItemId != null) {
                documentService.update(editingItemId, items.first { editingItemId == it.id })
            }
            copy(editingItemId = null)
        }
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
        val itemToDeleteId: Int? = null,
        val newName: String = "",
        val newPath: String = ""
    )
}

