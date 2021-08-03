package ru.dariamikhailukova.task3.mvp.presenter.show

import android.text.TextUtils
import ru.dariamikhailukova.task3.R
import ru.dariamikhailukova.task3.data.Note
import ru.dariamikhailukova.task3.data.NoteViewModel
import ru.dariamikhailukova.task3.mvp.view.show.ShowView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Презентер для фрагмента Show
 *
 * @property view вью, к которой имеем доступ через интерфейс
 * @property mNoteViewModel модель, с помощью которой осуществется доступ к БД
 */
class ShowFragmentPresenter(private var view: ShowView, private var mNoteViewModel: NoteViewModel): ShowPresenter {

    /**
     * Инициализация view
     */
    init {
        view.initView()
    }

    /**
     * Функция, вызывающая неявный интент "отправить заметку во внешнее приложение",
     * если заметка не пустая
     *
     * @param name название заметки
     * @param text текст заметки
     * @param date дата создания заметки
     */
    override fun sendEmail(name: String, text: String, date: String) {
        if(inputCheck(name, text)){
            view.sendIntent(name, text)
        }else{
            view.showToast(R.string.fill_all)
        }

    }

    /**
     * Функция, обновляющая заметку из [mNoteViewModel]
     *
     * @param name название заметки
     * @param text текст заметки
     */
    override fun update(name: String, text: String) {
        if(inputCheck(name,text)){
            val updatedNote = Note(view.currentNoteId(), name, text, getDate())

            mNoteViewModel.updateNote(updatedNote)
            view.showToast(R.string.update)
            view.returnToList()
        }else{
            view.showToast(R.string.fill_all)
        }
    }

    /**
     * Функция, удаляющая заметку из [mNoteViewModel]
     *
     * @param currentNote заметка
     */
    override fun delete(currentNote: Note) {
        mNoteViewModel.deleteNote(currentNote)
        view.showToast(R.string.remove)
        view.returnToList()
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