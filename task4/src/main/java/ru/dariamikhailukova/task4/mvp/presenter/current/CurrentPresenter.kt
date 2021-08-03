package ru.dariamikhailukova.task4.mvp.presenter.current

import ru.dariamikhailukova.task4.data.Note

interface CurrentPresenter {
    fun sendEmail(name: String, text: String, date: String)
    fun update(name: String, text: String)
    fun delete(currentNote: Note)
    fun inputCheck(name: String, text: String): Boolean
}