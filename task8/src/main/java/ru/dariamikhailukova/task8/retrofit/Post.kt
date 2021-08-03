package ru.dariamikhailukova.task8.retrofit

/**
 * Дата класс для скачивание "Поста" из сети
 *
 * @param userId ключ пользователя, создавшего пост
 * @param id ключ поста
 * @param title название поста
 * @param body текст поста
 */
data class Post (
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)