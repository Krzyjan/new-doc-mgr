package my.krzyjan.documentmgr.model

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.fail

@Disabled("Disabled until we can tackle paging again")
@OptIn(ExperimentalCoroutinesApi::class)
class DocumentPagingSourceTest {

    private val testDispatcher = StandardTestDispatcher()

    companion object {
        val instance: DocumentPagingSource = DocumentPagingSource(ExposedDocumentService())
        val pages = listOf(
            PagingSource.LoadResult.Page(
                TestConstants.someDocuments,
                null,
                TestConstants.someDocuments.size - GetPagedDocuments.PAGE_SIZE
            )
        )
    }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadPages() = runTest {

        val pagingState = PagingState(
            anchorPosition = GetPagedDocuments.INITIAL_PAGE_KEY,
            config = PagingConfig(pageSize = GetPagedDocuments.PAGE_SIZE),
            leadingPlaceholderCount = 0,
            pages = pages
        )
        pagingState.pages
        val pageNo = instance.getRefreshKey(pagingState)
        assertEquals(1, pageNo?.dec())
        val result = instance.load(
            PagingSource.LoadParams.Refresh(
                loadSize = pagingState.config.initialLoadSize,
                placeholdersEnabled = false,
                key = pageNo
            )
        )
        when (result) {
            is PagingSource.LoadResult.Page -> assertEquals(5, result.data.size)
            is PagingSource.LoadResult.Error -> fail(result.toString())
            is PagingSource.LoadResult.Invalid -> fail(result.toString())
        }

    }
}
