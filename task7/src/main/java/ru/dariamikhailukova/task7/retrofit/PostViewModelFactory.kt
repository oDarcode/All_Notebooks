  package ru.dariamikhailukova.task7.retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

  /**
   * Фабрика для PostViewModel
   *
   * @property repository абстракция через которую осуществляется доступ к данным
   */
  class PostViewModelFactory(
    private val repository: PostRepository
    ): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(repository) as T
    }

}