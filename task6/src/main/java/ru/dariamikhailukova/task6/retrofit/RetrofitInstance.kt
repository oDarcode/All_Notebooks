package ru.dariamikhailukova.task6.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.dariamikhailukova.task6.mvvm.view.add.AddFragment.Companion.BASE_URL

/**
 * Объект, который сохраняет retrofit состояние singleton
 */
object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: Api by lazy {
        retrofit.create(Api::class.java)
    }
}