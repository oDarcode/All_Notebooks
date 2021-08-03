package ru.dariamikhailukova.task8.retrofit

import retrofit2.Response

/**
 * Реализация паттерна Repository (Репозиторий).
 * Создает абстракцию, которая скрывает реализацию доступа к источнику данных.
 */
class PostRepository {

    suspend fun getPost(): Response<Post> {
        return RetrofitInstance.api.getPost()
    }
}