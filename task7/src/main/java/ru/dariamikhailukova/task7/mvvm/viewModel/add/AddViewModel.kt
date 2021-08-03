 package ru.dariamikhailukova.task7.mvvm.viewModel.add

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.dariamikhailukova.task7.singleLiveEvent.SingleLiveEvent
import ru.dariamikhailukova.task7.data.Note
import ru.dariamikhailukova.task7.data.NoteRepository
import java.util.*

 /**
  * View Model для фрагмента Add
  *
  * @property repository абстракция через которую осуществляется доступ к базе данных
  */
class AddViewModel(private val repository: NoteRepository): ViewModel() {

    var name = MutableLiveData<String>()
    var text = MutableLiveData<String>()

     /**
      * @property onAttemptSaveEmptyNote попытка сохранения пустой заметки
      */
    var onAttemptSaveEmptyNote: SingleLiveEvent<Unit> = SingleLiveEvent()

     /**
      * @property onSaveSuccess успешное сохранение заметки
      */
    val onSaveSuccess: SingleLiveEvent<Unit> = SingleLiveEvent()

     /**
      * Функция, которая добавляет заметку в [repository], если все поля заполнены
      */
    fun addNote(){
        if (inputCheck()){
            val note = Note(0, name.value.toString(), text.value.toString(), Date())
            viewModelScope.launch(Dispatchers.IO) {
                repository.addNote(note)
            }
            onSaveSuccess.call()
        }else{
            Log.d(TAG, "Не удалось сохранить заметку")
            onAttemptSaveEmptyNote.call()
        }
    }

     /**
      * @return true если оба поля заметки заполнены
      */
     private fun inputCheck(): Boolean{
        return !(name.value.isNullOrBlank() || text.value.isNullOrBlank())
    }

    companion object{
        const val TAG = "AddViewModel"
    }

}
