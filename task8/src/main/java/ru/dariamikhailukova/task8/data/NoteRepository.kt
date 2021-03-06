package ru.dariamikhailukova.task8.data

import androidx.lifecycle.LiveData

/**
 * Реализация паттерна Repository (Репозиторий).
 * Создает абстракцию, которая скрывает реализацию доступа к источнику данных.
 *
 * @param noteDao объект, который позволяет выполнять SQL – запросы к БД.
 *
 * Передаем DAO вместо всей базы данных, потому что нам необходим доступ только к данному объекту
 *
 */
class NoteRepository(private val noteDao: NoteDao) {
    /**
     * @property readAllData переменная, хранящая в себе список всех заметок БД
     */
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

    /**
     * Функция, осуществляющая поиск заметки в назании которой содержится последовательность символов
     *
     * @param searchQuery последовательность символов
     */
    fun searchDatabase(searchQuery: String): LiveData<List<Note>> {
        return noteDao.searchDatabase(searchQuery)
    }

}