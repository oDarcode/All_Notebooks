package ru.dariamikhailukova.task8.mvvm.view.show

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import ru.dariamikhailukova.task8.factory.MyViewModelFactory
import ru.dariamikhailukova.task8.factory.ViewModelTypes
import ru.dariamikhailukova.task8.mvvm.viewModel.show.ShowViewModel
import ru.dariamikhailukova.task8.workManager.MyWorker
import ru.dariamikhailukova.task8.R
import ru.dariamikhailukova.task8.data.Note
import ru.dariamikhailukova.task8.databinding.FragmentShowBinding

/**
 * View класс для работы с fragment_show
 */
class ShowFragment : Fragment(), ShowView {
    private lateinit var binding: FragmentShowBinding
    private lateinit var mShowViewModel: ShowViewModel

    private lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowBinding.inflate(inflater, container, false)

        val currentViewModelFactory = MyViewModelFactory(ViewModelTypes.CURRENT)
        mShowViewModel = ViewModelProvider(this, currentViewModelFactory).get(ShowViewModel::class.java)

        binding.showViewModel = mShowViewModel
        binding.lifecycleOwner = this

        subscribeToViewModel()

        return binding.root
    }

    /**
     * Заполнение вью информацией, содержащейся в заметке
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(NOTE) }?.apply {
            note = this.getParcelable(NOTE)!!
            mShowViewModel.initAll(note)
        }
    }

    /**
     * Функция, которая следит за изменением [mShowViewModel]
     */
    override fun subscribeToViewModel(){
        mShowViewModel.onAttemptSaveEmptyNote.observe(this){
            Toast.makeText(requireContext(), R.string.fill_all, Toast.LENGTH_SHORT).show()
        }

        mShowViewModel.onDeleteSuccess.observe(this){
            activity?.onBackPressed()
            Toast.makeText(requireContext(), R.string.remove, Toast.LENGTH_SHORT).show()
        }

        mShowViewModel.onUpdateSuccess.observe(this){
            activity?.onBackPressed()
            Toast.makeText(requireContext(), R.string.update, Toast.LENGTH_SHORT).show()
        }

        mShowViewModel.onSendSuccess.observe(this){
            sendIntent()
        }
    }

    /**
     * Функция, для сохранения полей открытой заметки в бд
     */
    override fun updateItem() {
        mShowViewModel.updateNote()
    }

    /**
     * Функция, которая производит резервное копирование заметки
     */
    override fun backup() {
        val myWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(mShowViewModel.doBackup())
            .build()

        WorkManager
            .getInstance(requireContext())
            .enqueue(myWorkRequest)
    }

    /**
     * Функция, которая выводит диалоговое окно для удаление открытой заметки из бд
     */
    override fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)){ _, _->
            mShowViewModel.deleteNote()
        }

        builder.apply {
            setNegativeButton(getString(R.string.no)){ _, _->}
            setTitle(getString(R.string.do_delete))
            setMessage(getString(R.string.are_you_sure))
            create().show()
        }
    }

    /**
     * Функция для отправки заметки во внешнее приложение
     */
    override fun sendEmail() {
        mShowViewModel.sendNote()
    }


    /**
     * Функция вызова интента - отправка текста во внешнее приложение
     */
    override fun sendIntent(){
        val name = binding.nameEditText.text.toString()
        val text = binding.textEditText.text.toString()
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "$name\n$text")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, ""))
    }

    companion object{
        const val NOTE = "Note"
    }
}