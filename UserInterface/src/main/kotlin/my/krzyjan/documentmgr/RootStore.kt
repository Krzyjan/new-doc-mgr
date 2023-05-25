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
    private fun initialState(): RootState =
        RootState(
            items = (1L .. 5L).map { id ->
                Document(name = "Document $id", path = "/documents/doc${id}")
            }
        )

    private inline fun setState(update: RootState.() -> RootState) {
        state.update()
    }

    data class RootState (
        val items: List<Document> = emptyList(),
        val editingItemId: Long? = null
    )
}

