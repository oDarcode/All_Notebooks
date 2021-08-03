package ru.dariamikhailukova.task2.presenter

interface Presenter {
    fun readNote(name: String, text: String)
    fun sendEmail()
}