package ru.dariamikhailukova.task4.mvp.presenter.add

interface AddPresenter {
    fun insertDataToDatabase(name: String, text: String)
    fun inputCheck(name: String, text: String): Boolean
    //fun getDate(): Date
}