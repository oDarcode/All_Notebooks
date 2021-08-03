package ru.dariamikhailukova.task3.mvp.presenter.add

import android.text.TextUtils
import ru.dariamikhailukova.task3.R
import ru.dariamikhailukova.task3.data.Note
import ru.dariamikhailukova.task3.data.NoteViewModel
import ru.dariamikhailukova.task3.mvp.view.add.AddView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Презентер для фрагмента Add
 *
 * @property view вью, к которой имеем доступ через интерфейс
 * @property mNoteViewModel модель, с помощью которой осуществется доступ к БД
 */
class AddFragmentPresenter(private var view: AddView, private var mNoteViewModel: NoteViewModel): AddPresenter {

    /**
     * Функция, которая добавляет заметку в [mNoteViewModel], если все поля заполнены
     *
     * @param name название заметки
     * @param text текст заметки
     */
    override fun insertDataToDatabase(name: String, text: String) {
        if (inputCheck(name, text)){
            val note = Note(0, name, text, getDate())

            mNoteViewModel.addNote(note)
            view.showToast(R.string.successfully)
            view.returnToList()
        }else{
            view.showToast(R.string.fill_all)
        }
    }

    /**
     * @return true если оба поля заметки заполнены
     *
     * @param name название заметки
     * @param text текст заметки
     */
    override fun inputCheck(name: String, text: String): Boolean{
        return !(TextUtils.isEmpty(name) || TextUtils.isEmpty(text))
    }

    /**
     * @return текущее время и дату в формате строки
     */
    override fun getDate(): String {
        val currentDate = Date()
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return timeFormat.format(currentDate) + " " + dateFormat.format(currentDate)
    }
}