package ru.dariamikhailukova.task4.mvp.view.current

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.dariamikhailukova.task4.R
import ru.dariamikhailukova.task4.data.Note
import ru.dariamikhailukova.task4.data.NoteViewModel
import ru.dariamikhailukova.task4.databinding.FragmentCurrentBinding
import ru.dariamikhailukova.task4.mvp.presenter.current.CurrentFragmentPresenter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * View класс для работы с fragment_current
 */
class CurrentFragment : Fragment(), CurrentView {
    private var presenter: CurrentFragmentPresenter? = null

    private lateinit var binding: FragmentCurrentBinding
    private lateinit var viewModel: NoteViewModel

    lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        presenter = CurrentFragmentPresenter(this, viewModel)

        return binding.root
    }

    /**
     * Заполнение вью информацией, содержащейся в заметке
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(NOTE) }?.apply {
            note = this.getParcelable(NOTE)!!

            binding.nameEditText.setText(note.name)
            binding.textEditText.setText(note.text)
            binding.dateTextView.text = getDate(note.date)
        }
    }

    /**
     * @return переводит дату в строковый формат
     *
     * @param currentDate дата
     */
    override fun getDate(currentDate: Date): String {
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return  timeFormat.format(currentDate) + "  " + dateFormat.format(currentDate)
    }

    /**
     * Функция, для сохранения полей открытой заметки в бд
     */
    override fun updateItem() {
        note.name = binding.nameEditText.text.toString()
        note.text = binding.textEditText.text.toString()

        presenter?.update(note.name, note.text)
    }

    /**
     * Функция, которая выводит диалоговое окно для удаление открытой заметки из бд
     */
    override fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->
            presenter?.delete(note)
        }

        builder.setNegativeButton("No"){_,_->}
        builder.setTitle("Delete ${note.name}?")
        builder.setMessage("Are you sure?")
        builder.create().show()
    }

    /**
     * Функция для вывода любого короткого сообения-тоста
     */
    override fun showToast(text: Int) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    /**
     * Функция вызова неявного интента "отправить заметку во внешнее приложение".
     *
     * @param name название заметки
     * @param text текст заметки
     */
    override fun sendIntent(name: String, text: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_SUBJECT, name)
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, ""))
    }

    /**
     * Функция, которая осуществляет переход из фрагмента Show к фрагменту List
     */
    override fun returnToList() {
        findNavController().navigate(R.id.action_viewPagerFragment_to_listFragment)
    }

    /**
     * @return id открытой заметки
     */
    override fun currentNoteId(): Long {
        return note.id
    }

    /**
     * Функция для отправки заметки во внешнее приложение
     */
    override fun sendTo(){
        presenter?.sendEmail(note.name, note.text, note.date.toString())
    }

    companion object {
        const val NOTE = "Note"
    }

}