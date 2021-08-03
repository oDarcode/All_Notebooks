package ru.dariamikhailukova.task4.mvp.view.current

import java.util.*

interface CurrentView {
    fun updateItem()
    fun deleteNote()
    fun showToast(text: Int)
    fun sendIntent(name: String, text: String)
    fun returnToList()
    fun currentNoteId(): Long
    fun getDate(currentDate: Date): String
    fun sendTo()
}