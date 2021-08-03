package ru.dariamikhailukova.task8.mvvm.view.current

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
import ru.dariamikhailukova.task8.mvvm.viewModel.current.CurrentViewModel
import ru.dariamikhailukova.task8.workManager.MyWorker
import ru.dariamikhailukova.task8.R
import ru.dariamikhailukova.task8.data.Note
import ru.dariamikhailukova.task8.databinding.FragmentCurrentBinding

/**
 * View класс для работы с fragment_show
 */
class CurrentFragment : Fragment(), CurrentView {
    private lateinit var binding: FragmentCurrentBinding
    private lateinit var mCurrentViewModel: CurrentViewModel

    lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentBinding.inflate(inflater, container, false)

        val currentViewModelFactory = MyViewModelFactory(requireContext(), ViewModelTypes.CURRENT)
        mCurrentViewModel = ViewModelProvider(this, currentViewModelFactory).get(CurrentViewModel::class.java)

        binding.currentViewModel = mCurrentViewModel
        binding.lifecycleOwner = this

        subscribeToViewModel()

        return binding.root
    }

    /**
     * Функция, которая следит за изменением [mCurrentViewModel]
     */
    private fun subscribeToViewModel(){
        mCurrentViewModel.onAttemptSaveEmptyNote.observe(this){
            Toast.makeText(requireContext(), R.string.fill_all, Toast.LENGTH_SHORT).show()
        }

        mCurrentViewModel.onDeleteSuccess.observe(this){
            activity?.onBackPressed()
            Toast.makeText(requireContext(), R.string.remove, Toast.LENGTH_SHORT).show()
        }

        mCurrentViewModel.onUpdateSuccess.observe(this){
            activity?.onBackPressed()
            Toast.makeText(requireContext(), R.string.update, Toast.LENGTH_SHORT).show()
        }

        mCurrentViewModel.onSendSuccess.observe(this){
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, mCurrentViewModel.name.value)
                putExtra(Intent.EXTRA_TEXT, mCurrentViewModel.text.value)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, ""))
        }

    }

    /**
     * Заполнение вью информацией, содержащейся в заметке
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(NOTE) }?.apply {
            note = this.getParcelable(NOTE)!!
            mCurrentViewModel.initAll(note)
        }
    }

    /**
     * Функция, для сохранения полей открытой заметки в бд
     */
    override fun updateItem() {
        mCurrentViewModel.updateNote()
    }

    /**
     * Функция, которая производит резервное копирование заметки
     *
     * или это лучше делать через какое-то время??
     */
    override fun backup() {
        val myWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(mCurrentViewModel.doBackup())
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
            mCurrentViewModel.deleteNote()
        }

        builder.setNegativeButton(getString(R.string.no)){ _, _->}
        builder.setTitle(getString(R.string.do_delete))
        builder.setMessage(getString(R.string.are_you_sure))
        builder.create().show()
    }

    /**
     * Функция для отправки заметки во внешнее приложение
     */
    override fun sendEmail() {
        mCurrentViewModel.sendNote()
    }


    companion object{
        const val NOTE = "Note"
    }
}