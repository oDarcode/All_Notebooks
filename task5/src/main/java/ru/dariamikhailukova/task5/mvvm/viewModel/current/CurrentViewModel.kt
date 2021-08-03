package ru.dariamikhailukova.task5.mvvm.viewModel.current

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.dariamikhailukova.task5.singleLiveEvent.SingleLiveEvent
import ru.dariamikhailukova.task5.data.Note
import ru.dariamikhailukova.task5.data.NoteRepository
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * View Model для фрагмента Current
 *
 * @property repository абстракция через которую осуществляется доступ к базе данных
 */
class CurrentViewModel(private val repository: NoteRepository): ViewModel() {

    var id = MutableLiveData<Long>()
    var name = MutableLiveData<String>()
    var text = MutableLiveData<String>()
    var date = MutableLiveData<Date>()

    var dateStr = MutableLiveData<String>()

    fun initAll(note: Note){
        id.value = note.id
        name.value = note.name
        text.value = note.text
        date.value = note.date
        dateStr.value = getDate()

    }

    /**
     * @property onAttemptSaveEmptyNote попытка сохранения пустой заметки
     */
    val onAttemptSaveEmptyNote = SingleLiveEvent<Unit>()

    /**
     * @property onDeleteSuccess успешное удаление заметки
     */
    val onDeleteSuccess = SingleLiveEvent<Unit>()

    /**
     * @property onUpdateSuccess успешное обновление заметки
     */
    val onUpdateSuccess = SingleLiveEvent<Unit>()

    /**
     * @property onSendSuccess успешная отправки заметки
     */
    val onSendSuccess = SingleLiveEvent<Unit>()


    /**
     * Функция, обновляющая заметку из [repository]
     */
    fun updateNote(){
        if (inputCheck()){
            val note = Note(id.value!!.toLong(), name.value.toString(), text.value.toString(), Date())
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateNote(note)
            }
            onUpdateSuccess.call()
        }else{
            Log.d(TAG, "Не удалось обновить заметку")
            onAttemptSaveEmptyNote.call()
        }
    }

    /**
     * Функция, удаляющая заметку из [repository]
     */
    fun deleteNote(){
        val note = Note(id.value!!.toLong(), name.value.toString(), text.value.toString(), date.value!!)

        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
        onDeleteSuccess.call()
    }

    /**
     * Функция, отправляющая заметку из [repository] во внешнее приложение
     */
    fun sendNote(){
        if (inputCheck()){
            onSendSuccess.call()
        }else{
            Log.d(TAG, "Не удалось отправить заметку")
            onAttemptSaveEmptyNote.call()
        }
    }

    /**
     * @return true если оба поля заметки заполнены
     */
    private fun inputCheck(): Boolean{
        return !(name.value.isNullOrBlank() || text.value.isNullOrBlank())
    }

    /**
     * @return дату создания заметки в формате строки
     */
    private fun getDate(): String {
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return  (timeFormat.format(date.value!!) + "  " + dateFormat.format(date.value!!))
    }

    companion object{
        const val TAG = "CurrentViewModel"
    }

}