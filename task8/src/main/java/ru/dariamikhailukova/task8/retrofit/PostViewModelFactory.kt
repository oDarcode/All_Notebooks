  package ru.dariamikhailukova.task8.retrofit

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

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(repository) as T
    }

}