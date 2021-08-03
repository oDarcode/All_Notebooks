package ru.dariamikhailukova.task4.mvp.presenter.viewPager

import androidx.lifecycle.LiveData
import ru.dariamikhailukova.task4.data.Note
import ru.dariamikhailukova.task4.data.NoteViewModel

class ViewPagerFragmentPresenter(private var mNoteViewModel: NoteViewModel): ViewPagerPresenter {

    /**
     * @return список всех заметок базы данных
     */
    override fun getAllData(): LiveData<List<Note>> {
        return mNoteViewModel.readAllData
    }
}