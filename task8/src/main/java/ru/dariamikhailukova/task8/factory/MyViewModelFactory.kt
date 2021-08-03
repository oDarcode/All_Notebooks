package ru.dariamikhailukova.task8.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.dariamikhailukova.task8.data.NoteDatabase
import ru.dariamikhailukova.task8.data.NoteRepository
import ru.dariamikhailukova.task8.mvvm.viewModel.add.AddViewModel
import ru.dariamikhailukova.task8.mvvm.viewModel.current.CurrentViewModel
import ru.dariamikhailukova.task8.mvvm.viewModel.list.ListViewModel
import ru.dariamikhailukova.task8.mvvm.viewModel.viewPager.ViewPagerModel

/**
 * Фабрика, которая создает view model для всех view приложения
 *
 * @param context контекст, для получения доступа к базе данных
 * @param type тип создаваемой view model
 */
class MyViewModelFactory(private val context: Context, private val type: ViewModelTypes): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
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