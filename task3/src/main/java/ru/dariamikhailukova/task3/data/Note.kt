package ru.dariamikhailukova.task3.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Дата класс для реализации базы данных "Заметки" на основе Room
 *
 * @param id ключ заметки
 * @param name название заметки
 * @param text текст заметки
 * @param date дата создания/изменения заметки
 */
@Parcelize
@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var text: String,
    var date: String
): Parcelable