package ru.dariamikhailukova.task4.mvp.presenter.current

import android.text.TextUtils
import ru.dariamikhailukova.task4.R
import ru.dariamikhailukova.task4.data.Note
import ru.dariamikhailukova.task4.data.NoteViewModel
import ru.dariamikhailukova.task4.mvp.view.current.CurrentView
import java.util.*

/**
 * Презентер для фрагмента Current
 *
 * @property view вью, к которой имеем доступ через интерфейс
 * @property mNoteViewModel модель, с помощью которой осуществется доступ к БД
 */
class CurrentFragmentPresenter(private var view: CurrentView, private var mNoteViewModel: NoteViewModel): CurrentPresenter {


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
            val updatedNote = Note(view.currentNoteId(), name, text, Date())

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
    override fun inputCheck(name: String, text: String): Boolean {
        return !(TextUtils.isEmpty(name) || TextUtils.isEmpty(text))
    }


}