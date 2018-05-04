package com.andonichc.bcng.data.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://wservice.viabicing.cat/"

abstract class ServiceBuilder<out T> {
    var logging = false
        private set

    abstract fun build(): T

    protected fun createRetrofit(): Retrofit =
            Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(createOkHttpClient())
                    .baseUrl(BASE_URL)
                    .build()

    fun logging(logging: Boolean) = apply { this.logging = logging }


    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (logging) {
            builder.addInterceptor(
                    HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }

        return builder.build()
    }
}