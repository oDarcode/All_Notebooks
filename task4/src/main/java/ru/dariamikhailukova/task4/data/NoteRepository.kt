package ru.dariamikhailukova.task4.data

import androidx.lifecycle.LiveData

/**
 * Реализация паттерна Repository (Репозиторий).
 * Создает абстракцию, которая скрывает реализацию доступа к источнику данных.
 *
 * @param noteDao объект, который позволяет выполнять SQL – запросы к БД
 */
class NoteRepository(private val noteDao: NoteDao) {
    val readAllData: LiveData<List<Note>> = noteDao.readAllData()

    /**
     * Функция, добавляющая заметку в БД
     * @param note заметка
     */
    suspend fun addNote(note: Note){
        noteDao.addNote(note)
    }

    /**
     * Функция, которая обновляет поля заметки в БД
     * @param note заметка
     */
    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    /**
     * Функция, удаляющая заметку из БД
     * @param note заметка
     */
    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }

    /**
     * Функция, удаляющая все заметки из БД
     */
    suspend fun deleteAllNotes(){
        noteDao.deleteAllNotes()
    }

}