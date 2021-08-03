package ru.dariamikhailukova.task8.mvvm.viewModel.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.dariamikhailukova.task8.singleLiveEvent.SingleLiveEvent
import ru.dariamikhailukova.task8.data.Note
import ru.dariamikhailukova.task8.data.NoteRepository

/**
 * View Model для фрагмента List
 *
 * @property repository абстракция через которую осуществляется доступ к базе данных
 */
class ListViewModel(private val repository: NoteRepository): ViewModel() {

    /**
     * @property readAllData список всех заметок, содержащихся в [repository]
     */
    val readAllData: LiveData<List<Note>> = repository.readAllData

    /**
     * @property onAllDeleteSuccess успешное удаление всех заметок
     */
    val onAllDeleteSuccess = SingleLiveEvent<Unit>()

    /**
     * Функция, удаляющая все заметки из [repository]
     */
    fun deleteAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllNotes()
        }
        onAllDeleteSuccess.call()
    }

    /**
     * Функция, которая осуществляет поиск заметки в [repository], в которой есть последовательность символов
     *
     * @param searchQuery последовательность символов
     */
    fun searchDatabase(searchQuery: String): LiveData<List<Note>> {
        return repository.searchDatabase(searchQuery)
    }

}