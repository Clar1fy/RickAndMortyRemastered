package com.timplifier.boilerplate.data.remote.dtos

data class DummyPagingResponse<T>(
    val prev: Int?,
    val next: Int?,
    val data: MutableList<T>
)