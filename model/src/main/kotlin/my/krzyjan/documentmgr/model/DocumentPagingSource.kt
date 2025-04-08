package my.krzyjan.documentmgr.model

import androidx.paging.PagingSource
import androidx.paging.PagingState

class DocumentPagingSource(
    private val documentService: DocumentService
): PagingSource<Int, Document>() {
    override fun getRefreshKey(state: PagingState<Int, Document>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Document> {
         return try {
            val offset = (params.key ?: 1).toLong()

            val documents = documentService.readPage(params.loadSize, offset) ?: emptyList()

            val nextOffset = when {
                (documents.size == params.loadSize) -> offset + params.loadSize
                else -> null
            }

            return LoadResult.Page(
                prevKey = null,
                nextKey = nextOffset?.toInt(),
                data = documents
            )
        } catch(exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}
