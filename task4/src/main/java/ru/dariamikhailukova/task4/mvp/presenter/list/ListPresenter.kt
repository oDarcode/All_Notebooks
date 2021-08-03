package ru.dariamikhailukova.task4.mvp.presenter.list

import androidx.lifecycle.LiveData
import ru.dariamikhailukova.task4.data.Note

interface ListPresenter {
    fun deleteAll()
    fun getAllData(): LiveData<List<Note>>
}