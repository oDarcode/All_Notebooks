package ru.dariamikhailukova.task4.mvp.presenter.add

import android.text.TextUtils
import ru.dariamikhailukova.task4.R
import ru.dariamikhailukova.task4.data.Note
import ru.dariamikhailukova.task4.data.NoteViewModel
import ru.dariamikhailukova.task4.mvp.view.add.AddView
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
            val note = Note(0, name, text, Date())

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
}