package my.krzyjan.documentmgr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import my.krzyjan.documentmgr.model.Document
import my.krzyjan.documentmgr.model.DocumentService
import org.kodein.di.DI
import org.kodein.di.instance

internal class ViewModel(val di: DI) {

    companion object RootStoreConstants {
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

                documentService.create(newItem)

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

    data class ModelState(
        val items: List<Document> = emptyList(),
        val editingItemId: Int? = null,
        val newName: String = "",
        val newPath: String = ""
    )
}

