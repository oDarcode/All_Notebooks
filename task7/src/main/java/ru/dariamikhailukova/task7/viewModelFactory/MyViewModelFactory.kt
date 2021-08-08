package ru.dariamikhailukova.task7.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.dariamikhailukova.task7.MainActivity.Companion.repository
import ru.dariamikhailukova.task7.mvvm.viewModel.add.AddViewModel
import ru.dariamikhailukova.task7.mvvm.viewModel.show.ShowViewModel
import ru.dariamikhailukova.task7.mvvm.viewModel.list.ListViewModel
import ru.dariamikhailukova.task7.mvvm.viewModel.viewPager.ViewPagerModel

/**
 * Фабрика, которая создает view model для всех view приложения
 *
 * @param type тип создаваемой view model
 */
class MyViewModelFactory(private val type: ViewModelTypes): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (type) {
            ViewModelTypes.ADD -> AddViewModel(repository) as T
            ViewModelTypes.CURRENT -> ShowViewModel(repository) as T
            ViewModelTypes.LIST -> ListViewModel(repository) as T
            ViewModelTypes.VIEW_PAGER -> ViewPagerModel(repository) as T
        }
    }
}