package com.timplifier.boilerplate.data.mapper

interface DataMapper<T, S> {
    fun T.mapToDomain(): S
}

fun <T : DataMapper<T, S>, S> T.mapToDomain() = this.mapToDomain()
