package ru.dariamikhailukova.task3.mvp.presenter.add

interface AddPresenter {
    fun insertDataToDatabase(name: String, text: String)
    fun inputCheck(name: String, text: String): Boolean
    fun getDate(): String
}