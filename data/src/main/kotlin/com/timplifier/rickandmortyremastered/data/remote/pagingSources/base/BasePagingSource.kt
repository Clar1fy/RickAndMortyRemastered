package com.timplifier.rickandmortyremastered.data.remote.pagingSources.base

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.timberta.rickandmortyremastered.common.mapper.DataMapper
import com.timberta.rickandmortyremastered.common.mapper.toDomain
import com.timplifier.rickandmortyremastered.data.remote.dtos.base.BaseRickAndMortyResponse
import retrofit2.HttpException
import java.io.IOException

abstract class BasePagingSource<Dto : DataMapper<Dto, Domain>, Domain : Any>(
    private val request: suspend (position: Int) -> BaseRickAndMortyResponse<Dto>,
) : PagingSource<Int, Domain>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Domain> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = request(position)
            val next = response.info.next
            val nextPageNumber = if (next == null) {
                null
            } else {
                Uri.parse(response.info.next).getQueryParameter(PAGE_QUERY)!!.toInt()
            }

            LoadResult.Page(
                data = response.results.map { it.toDomain() },
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Domain>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val PAGE_QUERY = "page"

    }
}