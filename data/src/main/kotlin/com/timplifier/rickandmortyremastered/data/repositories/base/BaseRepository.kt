package com.timplifier.rickandmortyremastered.data.repositories.base

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.timberta.rickandmortyremastered.common.either.Either
import com.timberta.rickandmortyremastered.common.mapper.DataMapper
import com.timplifier.rickandmortyremastered.data.remote.pagingSources.base.BasePagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal fun <T> makeNetworkRequest(
    request: suspend () -> T,
) = flow<Either<String, T>> {
    request().also { data ->
        emit(Either.Right(value = data))
    }

}.flowOn(Dispatchers.IO).catch { exception ->
    emit(Either.Left(exception.localizedMessage ?: "An error occurred"))
}

internal fun <ValueDto : DataMapper<ValueDto, Value>, Value : Any> makePagingRequest(
    pagingSource: BasePagingSource<ValueDto, Value>,
    pageSize: Int = 10,
    prefetchDistance: Int = pageSize,
    enablePlaceholders: Boolean = true,
    initialLoadSize: Int = pageSize * 3,
    maxSize: Int = Int.MAX_VALUE,
    jumpThreshold: Int = Int.MIN_VALUE
): Flow<PagingData<Value>> {
    return Pager(
        config = PagingConfig(
            pageSize,
            prefetchDistance,
            enablePlaceholders,
            initialLoadSize,
            maxSize,
            jumpThreshold
        ),
        pagingSourceFactory = {
            pagingSource
        }
    ).flow
}