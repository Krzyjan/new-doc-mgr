package my.krzyjan.documentmgr.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedDocuments @Inject constructor(
    private val documentService: DocumentService
) {
    operator fun invoke(
        viewModelScope: CoroutineScope
    ): Flow<PagingData<Document>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            initialKey = INITIAL_PAGE_KEY,
            pagingSourceFactory = {
                DocumentPagingSource(documentService)
            }
        ).flow.cachedIn(viewModelScope)
    }

    companion object {
        private const val INITIAL_PAGE_KEY = 1
        private const val PAGE_SIZE = 5
    }
}