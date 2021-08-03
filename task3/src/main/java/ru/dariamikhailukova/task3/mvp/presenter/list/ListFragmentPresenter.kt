package ru.dariamikhailukova.task3.mvp.presenter.list

import androidx.lifecycle.LiveData
import ru.dariamikhailukova.task3.R
import ru.dariamikhailukova.task3.data.Note
import ru.dariamikhailukova.task3.data.NoteViewModel
import ru.dariamikhailukova.task3.mvp.view.list.ListView

/**
 * Презентер для фрагмента List
 *
 * @property view вью, к которой имеем доступ через интерфейс
 * @property mNoteViewModel модель, с помощью которой осуществется доступ к БД
 */
class ListFragmentPresenter(private var view: ListView, private var mNoteViewModel: NoteViewModel): ListPresenter {

    /**
     * @return список всех заметок базы данных
     */
    override fun getAllData(): LiveData<List<Note>> {
        return mNoteViewModel.readAllData
    }

    /**
     * Функция, которая удаляет все заметки из [mNoteViewModel]
     */
    override fun deleteAll(){
        mNoteViewModel.deleteAllNotes()
        view.showToast(R.string.remove_all)
    }
}