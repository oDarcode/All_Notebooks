package ru.dariamikhailukova.task5.mvvm.viewModel.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import ru.dariamikhailukova.task5.data.NoteRepository

@RunWith(JUnit4::class)
class AddViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: NoteRepository
    private lateinit var viewModel: AddViewModel

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)
        repository = Mockito.mock(NoteRepository::class.java)
        viewModel = AddViewModel(repository)
    }

    //значение по умолчанию - ноль
    @Test
    fun isValueNull() {

        Assert.assertEquals(viewModel.name.value, null)
    }

    //правильно ли работает проверка для пустой строки и строки по умолчанию
    @Test
    fun isCorrectCheck() {
        viewModel.name.value = ""

        Assert.assertEquals(false, viewModel.inputCheck())
    }


    //сохраняется ли правильная заметка
    @Test
    fun addCorrectNote(){
        viewModel.name.value = "note"
        viewModel.text.value = "text"

        viewModel.addNote()
        var successSaved = false
        viewModel.onSaveSuccess.observeForever{
            successSaved = true
        }

        Assert.assertEquals(true, successSaved)
    }

    //сохранение неправильной заметки не происходит
    @Test
    fun addEmptyNote(){
        viewModel.name.value = null
        viewModel.text.value = "hello"

        viewModel.addNote()

        var savedError = false
        viewModel.onAttemptSaveEmptyNote.observeForever{
            savedError = true
        }

        Assert.assertEquals(true, savedError)

        var successSaved = false
        viewModel.onSaveSuccess.observeForever{
            successSaved = true
        }

        Assert.assertEquals(false, successSaved)
    }

    //заметка, текст которой одни пробелы не сохраняется
    @Test
    fun addNoteWithManySpaces(){
        viewModel.name.value = "first"
        viewModel.text.value = "        "

        viewModel.addNote()

        var savedError = false
        viewModel.onAttemptSaveEmptyNote.observeForever{
            savedError = true
        }

        Assert.assertEquals(true, savedError)
    }

}