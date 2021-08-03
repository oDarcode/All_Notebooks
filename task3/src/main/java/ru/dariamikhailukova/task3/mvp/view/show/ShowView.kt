package ru.dariamikhailukova.task3.mvp.view.show

interface ShowView {
    fun initView()
    fun updateItem()
    fun deleteNote()
    fun showToast(text: Int)
    fun sendIntent(name: String, text: String)
    fun returnToList()
    fun currentNoteId(): Int
}