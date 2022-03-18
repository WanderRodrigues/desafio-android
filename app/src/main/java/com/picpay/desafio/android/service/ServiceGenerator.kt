package com.picpay.desafio.android.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {

    val innerRetrofit: Retrofit by lazy {
        initRetrofit()
    }

    private val url = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

    private val gson: Gson by lazy { GsonBuilder().create() }

    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private fun initRetrofit(): Retrofit {
        return  Retrofit.Builder()
            .baseUrl(url)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    inline fun <reified T> generate(): T {
        try {
            return innerRetrofit.create(T::class.java)
        } catch (e: Exception) {
            throw Exception("Not found service ${T::class.java}/ $e")
        }
    }
}