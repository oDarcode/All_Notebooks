package ru.dariamikhailukova.task3.mvp.view.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.dariamikhailukova.task3.R
import ru.dariamikhailukova.task3.data.NoteViewModel
import ru.dariamikhailukova.task3.databinding.FragmentAddBinding
import ru.dariamikhailukova.task3.mvp.presenter.add.AddFragmentPresenter

/**
 * View класс для работы с fragment_add
 */
class AddFragment : Fragment(), AddView {
    private lateinit var binding: FragmentAddBinding

    private var presenter: AddFragmentPresenter? = null
    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        presenter = AddFragmentPresenter(this, viewModel)
        setHasOptionsMenu(true)

        return binding.root
    }

    /**
     * Функция для создания меню
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    /**
     * Функция, которая следит за нажатием кнопок меню
     *
     * @param item возможные элементы меню
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_save){
            val name = binding.textNoteName.text.toString()
            val text = binding.textNote.text.toString()

            presenter?.insertDataToDatabase(name, text)
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Функция, которая осуществляет переход из фрагмента Add к фрагменту List
     */
    override fun returnToList() {
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
    }

    /**
     * Функция для вывода любого короткого сообения-тоста
     */
    override fun showToast(text: Int) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

}