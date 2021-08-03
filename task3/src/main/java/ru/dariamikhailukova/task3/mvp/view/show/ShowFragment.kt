package ru.dariamikhailukova.task3.mvp.view.show

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.dariamikhailukova.task3.R
import ru.dariamikhailukova.task3.data.NoteViewModel
import ru.dariamikhailukova.task3.databinding.FragmentShowBinding
import ru.dariamikhailukova.task3.mvp.presenter.show.ShowFragmentPresenter

/**
 * View класс для работы с fragment_show
 */
class ShowFragment : Fragment(), ShowView {
    private var presenter: ShowFragmentPresenter? = null
    private val args by navArgs<ShowFragmentArgs>()

    private lateinit var binding: FragmentShowBinding
    private lateinit var viewModel: NoteViewModel

    private lateinit var name: String
    private lateinit var text: String
    private lateinit var date: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        presenter = ShowFragmentPresenter(this, viewModel)
        setHasOptionsMenu(true)

        return binding.root
    }

    /**
     * Заполнение вью информацией, содержащейся в заметке
     */
    override fun initView(){
        name = args.currentNote.name
        text = args.currentNote.text
        date = args.currentNote.date

        binding.updateTextNoteName.setText(name)
        binding.updateTextNote.setText(text)
        binding.updateTextDate.text = date
    }


    /**
     * Функция для создания меню
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.show_menu, menu)
    }

    /**
     * Функция, которая следит за нажатием кнопок меню
     *
     * @param item возможные элементы меню
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteNote()
        }

        if(item.itemId == R.id.menu_share){
            presenter?.sendEmail(name, text, date)
        }

        if(item.itemId == R.id.menu_save){
            updateItem()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Функция, для сохранения полей открытой заметки в бд
     */
    override fun updateItem(){
        name = binding.updateTextNoteName.text.toString()
        text = binding.updateTextNote.text.toString()
        //date = Integer.parseInt(binding.updateTextDate.text.toString())

        presenter?.update(name, text)
    }

    /**
     * Функция, которая выводит диалоговое окно для удаление открытой заметки из бд
     */
    override fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->
            presenter?.delete(args.currentNote)
        }

        builder.setNegativeButton("No"){_,_->}
        builder.setTitle("Delete ${args.currentNote.name}?")
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
        findNavController().navigate(R.id.action_showFragment_to_listFragment)
    }

    /**
     * @return id открытой заметки
     */
    override fun currentNoteId(): Int {
        return args.currentNote.id
    }



}