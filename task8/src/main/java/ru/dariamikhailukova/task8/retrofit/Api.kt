package ru.dariamikhailukova.task8.retrofit

import retrofit2.Response
import retrofit2.http.GET

/**
 * Интерфейс для работы с данными [Post]
 */
interface Api {

    @GET("posts/84")
    suspend fun getPost(): Response<Post>
}