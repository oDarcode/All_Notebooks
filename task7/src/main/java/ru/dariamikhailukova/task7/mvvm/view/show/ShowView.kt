package ru.dariamikhailukova.task7.mvvm.view.show

interface ShowView {
    fun subscribeToViewModel()
    fun updateItem()
    fun deleteNote()
    fun sendEmail()
    fun backup()
    fun sendIntent()

}