package com.timplifier.boilerplate.data.remote.pagingSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.timplifier.boilerplate.data.mapper.DataMapper
import com.timplifier.boilerplate.data.mapper.mapToDomain
import com.timplifier.boilerplate.data.remote.dtos.DummypagingResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BasePagingSource<ValueDto : DataMapper<ValueDto, Value>, Value : Any>(
    private val request: suspend (position: Int) -> Response<DummypagingResponse<ValueDto>>,
) : PagingSource<Int, Value>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
        val position = params.key ?: 1

        return try {
            val response = request(position)
            val data = response.body()!!

            LoadResult.Page(
                data = data.data.map { it.mapToDomain() },
                prevKey = null,
                nextKey = data.next
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}