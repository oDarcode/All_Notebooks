package ru.dariamikhailukova.task3.mvp.presenter.show

import ru.dariamikhailukova.task3.data.Note

interface ShowPresenter {
    fun sendEmail(name: String, text: String, date: String)
    fun update(name: String, text: String)
    fun delete(currentNote: Note)
    fun inputCheck(name: String, text: String): Boolean
    fun getDate(): String
}