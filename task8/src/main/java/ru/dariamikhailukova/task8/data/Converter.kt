package ru.dariamikhailukova.task8.data

import androidx.room.TypeConverter
import java.util.*

/**
 * Конвертер для хранения в базе данных даты
 *
 * Внутри базы данных дата хранится в формате Long,
 * но при извлечении ее формат преобразуется в Date.
 *
 * Позволяет учесть часовые пояса и хранить дату более точно.
 */
class Converter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}