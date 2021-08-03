package ru.dariamikhailukova.task6.mvvm.viewModel.viewPager

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.dariamikhailukova.task6.data.Note
import ru.dariamikhailukova.task6.data.NoteRepository

/**
 * View Model для фрагмента ViewPager2
 *
 * @property repository абстракция через которую осуществляется доступ к базе данных
 */
class ViewPagerModel(private val repository: NoteRepository): ViewModel() {

    /**
     * @property readAllData список всех заметок, содержащихся в [repository]
     */
    val readAllData: LiveData<List<Note>> = repository.readAllData
}