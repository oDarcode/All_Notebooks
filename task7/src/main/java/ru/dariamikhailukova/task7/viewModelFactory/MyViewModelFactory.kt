package ru.dariamikhailukova.task7.viewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.dariamikhailukova.task7.data.NoteDatabase
import ru.dariamikhailukova.task7.data.NoteRepository
import ru.dariamikhailukova.task7.mvvm.viewModel.add.AddViewModel
import ru.dariamikhailukova.task7.mvvm.viewModel.current.CurrentViewModel
import ru.dariamikhailukova.task7.mvvm.viewModel.list.ListViewModel
import ru.dariamikhailukova.task7.mvvm.viewModel.viewPager.ViewPagerModel

/**
 * Фабрика, которая создает view model для всех view приложения
 *
 * @param context контекст, для получения доступа к базе данных
 * @param type тип создаваемой view model
 */
class MyViewModelFactory(private val context: Context, private val type: ViewModelTypes): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val noteDao = NoteDatabase.getDatabase(context).noteDao()
        val repository = NoteRepository(noteDao)

        return when (type) {
            ViewModelTypes.ADD -> AddViewModel(repository) as T
            ViewModelTypes.CURRENT -> CurrentViewModel(repository) as T
            ViewModelTypes.LIST -> ListViewModel(repository) as T
            ViewModelTypes.VIEW_PAGER -> ViewPagerModel(repository) as T
        }
    }
}