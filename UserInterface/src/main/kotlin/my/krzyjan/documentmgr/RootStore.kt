package my.krzyjan.documentmgr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import my.krzyjan.documentmgr.model.Document

internal class RootStore {
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
            items = (1L..5L).map { id ->
                Document(name = "Document $id", path = "/documents/doc${id}")
            }
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

