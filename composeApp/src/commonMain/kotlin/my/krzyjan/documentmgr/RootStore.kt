package my.krzyjan.documentmgr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import my.krzyjan.documentmgr.RootStore.RootStoreConstants.INITIAL_PAGE_SIZE
import my.krzyjan.documentmgr.model.Document
import my.krzyjan.documentmgr.model.DocumentService
import org.kodein.di.DI
import org.kodein.di.instance

internal class RootStore(val di: DI) {

    companion object RootStoreConstants {
        const val INITIAL_PAGE_SIZE = 5
    }

    private val documentService:DocumentService by di.instance<DocumentService>()

    var state: RootState by mutableStateOf(initialState())
        private set

    fun onItemClicked(id: Long) {
        setState { copy(editingItemId = id) }
    }

    fun onAddItemClicked() {
        if (state.newName.isNotBlank() && state.newPath.isNotBlank()) {
            setState {
                val newItem = Document(name = newName, path = newPath)

                copy(items = items + newItem, newName = "", newPath = "")
            }
        }
    }

    fun onNameChanged(text: String) {
        setState { copy(newName = text) }
    }

    fun onPathChanged(text: String) {
        setState { copy(newPath = text) }
    }

    private fun initialState(): RootState =
        RootState(
            items = documentService.readPage(INITIAL_PAGE_SIZE)?: emptyList()
        )

    private inline fun setState(update: RootState.() -> RootState) {
        state = state.update()
    }

    data class RootState(
        val items: List<Document> = emptyList(),
        val editingItemId: Long? = null,
        val newName: String = "",
        val newPath: String = ""
    )
}

