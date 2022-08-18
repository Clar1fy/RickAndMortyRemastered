package com.timplifier.boilerplate.data.remote

import retrofit2.Retrofit
import javax.inject.Inject

class NetworkClient @Inject constructor(
) {
    private val retrofit =
        provideRetrofit(
            provideOkHttpClientBuilder().build()

        )

    class AuthClient @Inject constructor(
    ) {
        private val retrofit = provideRetrofit(
            provideOkHttpClientBuilder()
                .build()
        )
    }
}

inline fun <reified T : Any> Retrofit.createAnApi(): T {
    return create(T::class.java)
}