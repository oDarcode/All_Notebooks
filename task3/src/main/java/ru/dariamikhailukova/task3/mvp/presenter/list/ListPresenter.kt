package ru.dariamikhailukova.task3.mvp.presenter.list

import androidx.lifecycle.LiveData
import ru.dariamikhailukova.task3.data.Note

interface ListPresenter {
    fun deleteAll()
    fun getAllData(): LiveData<List<Note>>
}