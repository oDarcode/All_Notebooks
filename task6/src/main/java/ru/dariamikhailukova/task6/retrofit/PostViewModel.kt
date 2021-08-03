package ru.dariamikhailukova.task6.retrofit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * View Model для фрагмента Main
 *
 * @property repository абстракция через которую осуществляется доступ к данным
 */
class PostViewModel(private val repository: PostRepository): ViewModel() {

    val myResponse: MutableLiveData<Response<Post>> = MutableLiveData()

    /**
     * Функция, получающая пост из сети
     */
    fun getPost() {
        viewModelScope.launch {
            val response: Response<Post> = repository.getPost()
            myResponse.value = response
        }
    }
}